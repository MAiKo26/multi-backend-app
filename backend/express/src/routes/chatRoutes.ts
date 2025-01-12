import {Router} from "express";
import {getHistoryMessages} from "../services/chatService.ts";

const router = Router();

router.get("/:roomId", getHistoryMessages);

export default router;
