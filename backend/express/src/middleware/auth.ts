import {Request, Response, NextFunction} from "express";
import jwt from "jsonwebtoken";
const secret = process.env.JWT_SECRET!;

declare global {
  namespace Express {
    interface Request {
      user?: string | jwt.JwtPayload | undefined;
    }
  }
}

export const authenticateToken = (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  // I take the first one because "Bearer Token" is the format and I need the token
  const token = req.headers.authorization?.split(" ")[1];
  if (!token) return res.status(401).json({message: "No token provided"});

  jwt.verify(token, secret, (err, user) => {
    if (err) return res.status(403).json({message: "Invalid token"});
    req.user = user;
    next();
  });
};
