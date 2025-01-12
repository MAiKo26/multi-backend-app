import {and, count, eq, exists, gt} from "drizzle-orm";
import {NextFunction, Request, Response} from "express";
import {db} from "../db/db.ts";
import {sessions, teamMembers, users} from "../db/schema.ts";
import {CustomError} from "../lib/custom-error.ts";

export async function getAllUsers(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const allUsers = await db.query.users.findMany({
      columns: {
        email: true,
        avatar: true,
        password: true,
        name: true,
        role: true,
        phoneNumber: true,
        lastLogin: true,
      },
    });

    res.status(200).json(allUsers);
  } catch (error) {
    next(error);
  }
}

export async function getAllUsersByTeam(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const teamId = Number(req.params.team);

    const allUsersByTeam = await db.query.teamMembers.findMany({
      where: eq(teamMembers.teamId, teamId),
      with: {
        users: {
          columns: {
            email: true,
            avatar: true,
            password: true,
            name: true,
            role: true,
            phoneNumber: true,
            lastLogin: true,
          },
        },
      },
    });

    res.status(200).json(allUsersByTeam.map((item) => item.users));
  } catch (error) {
    next(error);
  }
}

export async function getUserDetailsBySession(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const session = req.params.session;

    if (!session) {
      res.status(400).json({message: "No session ID provided"});
      return;
    }

    const userWithSession = await db
      .select({
        sessionId: sessions.sessionId,
        email: users.email,
        name: users.name,
        avatar: users.avatar,
        role: users.role,
        phoneNumber: users.phoneNumber,
        lastLogin: users.lastLogin,
      })
      .from(sessions)
      .innerJoin(users, eq(users.email, sessions.email))
      .where(eq(sessions.sessionId, session))
      .limit(1);

    res.status(200).json(userWithSession[0]);
  } catch (error) {
    next(error);
  }
}

export async function getConnectedUsers(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const connectedUsers = await db
      .select({email: users.email})
      .from(users)
      .where(
        exists(
          db
            .select()
            .from(sessions)
            .where(
              and(
                eq(sessions.email, users.email),
                gt(sessions.expiresAt, new Date())
              )
            )
        )
      );
    res.status(200).json(connectedUsers);
  } catch (error) {
    next(error);
  }
}
