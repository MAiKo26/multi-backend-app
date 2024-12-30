import {Request, Response, NextFunction} from "express";
import {CustomError} from "../lib/custom-error.ts";

export function error(
  err: CustomError,
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const message = JSON.parse(err.message);
    res.status(err.statusCode).json({message});
  } catch (error) {
    res.status(500).json({message: err.message});
  }
}
