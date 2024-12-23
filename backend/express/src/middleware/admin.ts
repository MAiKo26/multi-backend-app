import {Request, Response, NextFunction} from "express";
import jwt from "jsonwebtoken";
import {db} from "../db/db.ts";
import {eq} from "drizzle-orm";
import {UserRole, users} from "../db/schema.ts";
import {CustomError} from "../lib/custom-error.ts";
const secret = process.env.JWT_SECRET!;

const adminMiddleware = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const token = req.headers.authorization?.split(" ")[1];
  if (!token) {
    throw new CustomError("No token provided", 401);
  }
  const decoded = jwt.verify(token, secret) as {
    email: string;
    role: UserRole;
    iat: number;
    exp: number;
  };
  const user = await db.query.users.findFirst({
    where: eq(users.email, decoded.email),
    columns: {role: true},
  });
  if (!decoded || user?.role !== "admin") {
    throw new CustomError("No token provided", 403);
  }
  next();
};
