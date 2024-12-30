import {Router} from "express";
import {
  getActivityByEmail,
  getAllActivities,
} from "../services/activityServices.ts";

const router = Router();

router.get("/", getAllActivities);

router.get("/user/:email", getActivityByEmail);

export default router;
