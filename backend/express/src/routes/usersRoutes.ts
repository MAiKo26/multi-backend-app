import {Router} from "express";
import {
  getAllUsers,
  getAllUsersByTeam,
  getUserDetailsBySession,
} from "../services/usersServices.ts";

const router = Router();

router.get("/", getAllUsers);

router.get("/byteam/:team", getAllUsersByTeam);

router.get("/bysession/:session", getUserDetailsBySession);

export default router;
