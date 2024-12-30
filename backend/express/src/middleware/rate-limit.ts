import {rateLimit} from "express-rate-limit";
import {CustomError} from "../lib/custom-error.ts";

export const apiLimiter = rateLimit({
  windowMs: 10 * (60 * 1000),
  max: 10000,
  message: "Too many requests from this IP, please try again later",
  handler: (req, res, next) => {
    next(new CustomError("Too many requests, please try again later", 429));
  },
});

// Stricter limiter for auth routes
export const authLimiter = rateLimit({
  windowMs: 60 * (60 * 1000),
  max: 20,
  message: "Too many login attempts, please try again later",
  handler: (req, res, next) => {
    next(
      new CustomError("Too many login attempts, please try again later", 429)
    );
  },
});
