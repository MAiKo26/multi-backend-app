import crypto from "crypto";
import {eq} from "drizzle-orm";
import {NextFunction, Request, Response} from "express";
import {db} from "../db/db.ts";
import {projectMembers, projects} from "../db/schema.ts";
import {logActivity} from "../lib/activity-logger.ts";
import {CustomError} from "../lib/custom-error.ts";

export async function getAllProjects(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {teamId} = req.params;

    if (!teamId) throw new CustomError("No team provided");

    const allProjects = await db.query.projects.findMany({
      columns: {
        id: true,
        name: true,
        createdAt: true,
        teamId: true,
      },
      with: {tasks: true},
      where: eq(projects.teamId, parseInt(teamId)),
    });

    res.status(200).json(allProjects);
  } catch (error) {
    next(error);
  }
}

// Create project
export async function createProject(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {name, teamId} = req.body;
    const projectId = crypto.randomBytes(16).toString("hex");

    const newProject = await db
      .insert(projects)
      .values({
        id: projectId,
        name,
        teamId,
        createdAt: new Date(),
      })
      .returning();

    await logActivity(
      req.user?.email as string,
      `Created new project: ${name} in team ${teamId}`
    );

    res.status(201).json(newProject[0]);
  } catch (error) {
    next(error);
  }
}

// Add member to project
export async function addMembersToProject(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {projectId, email} = req.body;

    const existingMember = await db.query.projectMembers.findFirst({
      where:
        eq(projectMembers.email, email) &&
        eq(projectMembers.projectId, projectId),
    });

    if (existingMember) {
      res.status(400).json({message: "User already in project"});
      return;
    }

    await db.insert(projectMembers).values({
      projectId,
      email,
    });

    await logActivity(
      req.user?.email as string,
      `Added user ${email} to project ${projectId}`
    );

    res.status(200).json({message: "Member added successfully"});
  } catch (error) {
    next(error);
  }
}

// Delete project
export async function deleteProject(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {projectId} = req.params;

    const project = await db.query.projects.findFirst({
      where: eq(projects.id, projectId),
    });

    if (!project) {
      res.status(404).json({message: "Project not found"});
      return;
    }

    await db.delete(projects).where(eq(projects.id, projectId));

    await logActivity(
      req.user?.email as string,
      `Deleted project: ${project.name}`
    );

    res.status(200).json({message: "Project deleted successfully"});
  } catch (error) {
    next(error);
  }
}
