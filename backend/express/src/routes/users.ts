import {NextFunction, Request, Response, Router} from "express";
import {db} from "../db/db.ts";
import jwt from "jsonwebtoken";
import {sessions, users} from "../db/schema.ts";
import {eq} from "drizzle-orm";

const secret = process.env.JWT_SECRET!;

const router = Router();

router.get("/", async (req: Request, res: Response, next: NextFunction) => {
  try {
    const users = await db.query.users.findMany({
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

    res.status(200).json(users);
  } catch (error) {
    next(error);
  }
});

router.get(
  "/bysession/:session",
  async (req: Request, res: Response, next: NextFunction) => {
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
);

export default router;
