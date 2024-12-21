import {NextFunction, Request, Response, Router} from "express";
import {db} from "../db/db.ts";

const secret = process.env.JWT_SECRET!;

const router = Router();

router.get("/", async (req: Request, res: Response, next: NextFunction) => {
  try {
    const projects = await db.query.projects.findMany({
      columns: {
        id: true,
        name: true,
        createdAt: true,
      },
    });

    res.status(200).json(projects);
  } catch (error) {
    next(error);
  }
});

export default router;
