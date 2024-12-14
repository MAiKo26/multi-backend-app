import {NextFunction, Request, Response, Router} from "express";
import {db} from "../db/db.ts";

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

    const emailArray = users.map((email) => email.email);

    res.status(200).json(users);
  } catch (error) {
    next(error);
  }
});

export default router;
