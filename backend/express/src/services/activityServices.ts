import {eq} from "drizzle-orm";
import {NextFunction, Request, Response} from "express";
import {db} from "../db/db.ts";
import {activityHistory} from "../db/schema.ts";

// Get all activities
export async function getAllActivities(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const activities = await db.query.activityHistory.findMany({
      orderBy: (history) => history.doneAt,
      with: {
        users: {
          columns: {
            email: true,
            name: true,
            avatar: true,
          },
        },
      },
    });

    res.status(200).json(activities);
  } catch (error) {
    next(error);
  }
}

// Get activities for a specific user
export async function getActivityByEmail(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {email} = req.params;

    const activities = await db.query.activityHistory.findMany({
      where: eq(activityHistory.userId, email),
      orderBy: (history) => history.doneAt,
      with: {
        users: {
          columns: {
            email: true,
            name: true,
            avatar: true,
          },
        },
      },
    });

    res.status(200).json(activities);
  } catch (error) {
    next(error);
  }
}
