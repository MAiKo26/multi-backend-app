import {eq} from "drizzle-orm";
import {NextFunction, Request, Response} from "express";
import {db} from "../db/db.ts";
import {UserRole, users} from "../db/schema.ts";

export const isAdmin = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  try {
    const email = req.user?.email as string;
    const user = await db.query.users.findFirst({
      where: eq(users.email, email),
    });

    if (user?.role !== UserRole.ADMIN) {
      res.status(403).json({message: "Admin access required"});
      return;
    }
    next();
  } catch (error) {
    next(error);
  }
};
