import {eq, and} from "drizzle-orm";
import {NextFunction, Request, Response, Router} from "express";
import {db} from "../db/db.ts";
import {teams, teamMembers, users, UserRole} from "../db/schema.ts";
import {logActivity} from "../lib/activity-logger.ts";

// Get all teams (admin only)
export async function getAllTeams(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const allTeams = await db.query.teams.findMany({
      with: {
        teamMembers: {
          with: {
            users: true,
          },
        },
      },
    });
    res.status(200).json(allTeams);
  } catch (error) {
    next(error);
  }
}

// Create team (admin only)
export async function createTeam(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {name, memberEmails} = req.body;

    const newTeam = await db
      .insert(teams)
      .values({
        name,
        createdAt: new Date(),
      })
      .returning();

    await logActivity(req.user?.email as string, `Created new team: ${name}`);

    if (memberEmails && memberEmails.length > 0) {
      await db.insert(teamMembers).values(
        memberEmails.map((email: string) => ({
          teamId: newTeam[0].id,
          email: email,
        }))
      );
    }

    res.status(201).json(newTeam[0]);
  } catch (error) {
    next(error);
  }
}

// Update team (admin only)
export async function updateTeam(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {id} = req.params;
    const {name} = req.body;

    const updatedTeam = await db
      .update(teams)
      .set({name})
      .where(eq(teams.id, parseInt(id)))
      .returning();

    res.status(200).json(updatedTeam[0]);
  } catch (error) {
    next(error);
  }
}

// Delete team (admin only)
export async function deleteTeam(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {id} = req.params;
    const team = await db.query.teams.findFirst({
      where: eq(teams.id, parseInt(id)),
    });

    if (!team) {
      res.status(404).json({message: "Team not found"});
      return;
    }

    await db.delete(teams).where(eq(teams.id, parseInt(id)));

    await logActivity(req.user?.email as string, `Deleted team: ${team.name}`);

    res.status(200).json({message: "Team deleted successfully"});
  } catch (error) {
    next(error);
  }
}

// Add member to team
export async function addMemberToTeam(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {teamId, email} = req.body;

    const existingMember = await db.query.teamMembers.findFirst({
      where: and(eq(teamMembers.teamId, teamId), eq(teamMembers.email, email)),
    });

    if (existingMember) {
      res.status(400).json({message: "User already in team"});
      return;
    }

    await db.insert(teamMembers).values({
      teamId,
      email,
    });

    await logActivity(
      req.user?.email as string,
      `Added user ${email} to team ${teamId}`
    );

    res.status(200).json({message: "Member added successfully"});
  } catch (error) {
    next(error);
  }
}

// Get teams by user email
export async function getTeamsByUserEmail(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {email} = req.params;

    const teams = await db.query.teams.findMany({
      columns: {
        id: true,
        name: true,
        createdAt: true,
      },
      with: {
        teamMembers: {
          where: eq(teamMembers.email, email),
        },
      },
      where: (teams, {exists}) =>
        exists(
          db
            .select()
            .from(teamMembers)
            .where(
              and(
                eq(teamMembers.teamId, teams.id),
                eq(teamMembers.email, email)
              )
            )
        ),
    });

    res.status(200).json(teams);
  } catch (error) {
    next(error);
  }
}
