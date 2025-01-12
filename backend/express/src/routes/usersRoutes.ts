import {Router} from "express";
import {
  getAllUsers,
  getAllUsersByTeam,
  getConnectedUsers,
  getUserDetailsBySession,
} from "../services/usersServices.ts";

const router = Router();

router.get("/", getAllUsers);

router.get("/byteam/:team", getAllUsersByTeam);

router.get("/bysession/:session", getUserDetailsBySession);

router.get("/online", getConnectedUsers);

export default router;
