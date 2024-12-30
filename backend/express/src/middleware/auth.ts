import {Request, Response, NextFunction} from "express";
import jwt from "jsonwebtoken";
import {UserRole} from "../db/schema.ts";
import {CustomError} from "../lib/custom-error.ts";

declare global {
  namespace Express {
    interface Request {
      user?: {
        email: string;
        role: UserRole;
      };
    }
  }
}

const secret = process.env.JWT_SECRET!;

export const authenticateToken = (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const token = req.headers.authorization?.split(" ")[1];

  if (!token || token === "undefined") {
    throw new CustomError("No Token Provided", 401);
  }

  console.log(token);

  try {
    const decoded = jwt.verify(token, secret) as {
      email: string;
      role: UserRole;
      iat: number;
      exp: number;
    };

    console.log(decoded);

    req.user = decoded;
    next();
  } catch (error) {
    console.log("i guess", error);

    next(new CustomError("Invalid Token", 403));
  }
};

export const authorize = (roles: UserRole[] = []) => {
  return (req: Request, res: Response, next: NextFunction) => {
    if (!req.user) {
      throw new CustomError("Not authenticated", 401);
    }

    if (roles.length && !roles.includes(req.user.role)) {
      throw new CustomError("Insufficient permissions", 403);
    }

    next();
  };
};

export const authorizeAdmin = authorize([UserRole.ADMIN]);
export const authorizeUser = authorize([UserRole.USER]);
export const authorizeAll = authorize([UserRole.ADMIN, UserRole.USER]);
