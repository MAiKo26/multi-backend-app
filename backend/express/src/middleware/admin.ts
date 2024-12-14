import {Request, Response, NextFunction} from "express";
import jwt from "jsonwebtoken";
import {db} from "../db/db.ts";
import {eq} from "drizzle-orm";
import {users} from "../db/schema.ts";
const secret = process.env.JWT_SECRET!;

const adminMiddleware = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const token = req.headers.authorization?.split(" ")[1];
  if (!token) {
    return res.status(401).json({message: "No token provided"});
  }
  const decoded = String(jwt.verify(token, secret));
  const user = await db.query.users.findFirst({
    where: eq(users.email, decoded),
    columns: {role: true},
  });
  if (!decoded || user?.role !== "admin") {
    return res.status(403).json({message: "Forbidden"});
  }
  next();
};
