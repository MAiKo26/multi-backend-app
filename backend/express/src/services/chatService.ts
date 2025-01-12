import {NextFunction, Request, Response} from "express";
import {db} from "../db/db.ts";
import {chatMessages, chatRooms} from "../db/schema.ts";
import {asc, eq} from "drizzle-orm";
import {ChatMessage} from "./webSocketService.ts";

export async function getHistoryMessages(
  req: Request,
  res: Response,
  next: NextFunction
) {
  try {
    const {roomId} = req.params;

    const messages = await db.query.chatMessages.findMany({
      where: eq(chatMessages.roomId, roomId),
      orderBy: [asc(chatMessages.createdAt)],
    });

    res.status(200).json(messages);
  } catch (error) {
    next(error);
  }
}

export async function initializeRoom(roomId: string) {
  try {
    const existingRoom = await db.query.chatRooms.findFirst({
      where: eq(chatRooms.id, roomId),
    });

    if (!existingRoom) {
      await db.insert(chatRooms).values({
        id: roomId,
      });
    }
  } catch (error) {
    console.error(`Error initializing room ${roomId}:`, error);
  }
}

export async function saveMessage(roomId: string, message: ChatMessage) {
  try {
    await db.insert(chatMessages).values({
      roomId: roomId,
      senderId: message.senderId,
      content: message.content,
      createdAt: new Date(message.createdAt),
    });
  } catch (error) {
    console.error(`Error saving message to room ${roomId}:`, error);
  }
}
