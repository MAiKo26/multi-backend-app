import {eq} from "drizzle-orm";
import {NextFunction, Request, Response, Router} from "express";
import {db} from "../db/db.ts";
import {tasks, taskComments, starredTasks} from "../db/schema.ts";
import crypto from "crypto";
import {logActivity} from "../lib/activity-logger.ts";

export async function getAllTasks(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const allTasks = await db.query.tasks.findMany({
      columns: {
        id: true,
        name: true,
        description: true,
        createdAt: true,
        stars: true,
        finished: true,
        projectId: true,
        finishedBy: true,
      },
      with: {
        taskComments: {columns: {id: true, content: true, createdAt: true}},
        starredTasks: {columns: {createdAt: true, taskId: true, userId: true}},
      },
    });

    res.status(200).json(allTasks);
  } catch (error) {
    next(error);
  }
}

// Get all tasks for a project
export async function getAllTasksForProject(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {projectId} = req.params;
    const projectTasks = await db.query.tasks.findMany({
      where: eq(tasks.projectId, projectId),
      with: {
        taskComments: {
          with: {
            users: true,
          },
        },
      },
    });

    res.status(200).json(projectTasks);
  } catch (error) {
    next(error);
  }
}

// Create task
export async function createTask(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {name, description, projectId} = req.body;
    const taskId = crypto.randomBytes(16).toString("hex");

    const newTask = await db
      .insert(tasks)
      .values({
        id: taskId,
        name,
        description,
        projectId,
        createdAt: new Date(),
      })
      .returning();

    await logActivity(
      req.user?.email as string,
      `Created new task: ${name} in project ${projectId}`
    );

    res.status(201).json(newTask[0]);
  } catch (error) {
    next(error);
  }
}

// Update task
export async function updateTask(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {taskId} = req.params;
    const {name, description, finished} = req.body;

    const updatedTask = await db
      .update(tasks)
      .set({
        name,
        description,
        finished,
        finishedBy: finished ? req.user?.email : null,
      })
      .where(eq(tasks.id, taskId))
      .returning();

    if (finished) {
      await logActivity(
        req.user?.email as string,
        `Completed task: ${updatedTask[0].name}`
      );
    }

    res.status(200).json(updatedTask[0]);
  } catch (error) {
    next(error);
  }
}

// Delete task
export async function deleteTask(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {taskId} = req.params;
    await db.delete(tasks).where(eq(tasks.id, taskId));
    res.status(200).json({message: "Task deleted successfully"});
  } catch (error) {
    next(error);
  }
}

// Add comment to task
export async function addCommentToTask(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {taskId} = req.params;
    const {content} = req.body;
    const commentId = crypto.randomBytes(16).toString("hex");

    const newComment = await db
      .insert(taskComments)
      .values({
        id: commentId,
        taskId,
        userId: req.user?.email,
        content,
        createdAt: new Date(),
      })
      .returning();

    await logActivity(
      req.user?.email as string,
      `Commented on task ${taskId}: ${content.substring(0, 30)}${
        content.length > 30 ? "..." : ""
      }`
    );

    res.status(201).json(newComment[0]);
  } catch (error) {
    next(error);
  }
}

// Star/unstar task
export async function starringTask(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {taskId} = req.params;
    const userEmail = req.user?.email as string;

    const existingStar = await db.query.starredTasks.findFirst({
      where:
        eq(starredTasks.taskId, taskId) && eq(starredTasks.userId, userEmail),
    });

    if (existingStar) {
      await db
        .delete(starredTasks)
        .where(
          eq(starredTasks.taskId, taskId) && eq(starredTasks.userId, userEmail)
        );

      await logActivity(userEmail, `Unstarred task ${taskId}`);
    } else {
      await db.insert(starredTasks).values({
        taskId,
        userId: userEmail,
        createdAt: new Date(),
      });

      await logActivity(userEmail, `Starred task ${taskId}`);
    }

    res.status(200).json({message: "Task starred"});
  } catch (error) {
    next(error);
  }
}
