import {WebsocketRequestHandler} from "express-ws";
import WebSocket from "ws";
import {initializeRoom, saveMessage} from "./chatService.ts";

export interface ChatMessage {
  roomId: string;
  id: number;
  readBy: string[];
  senderId: string;
  createdAt: string;
  content: string;
}

// Store rooms and their clients
const rooms = new Map<string, Set<WebSocket>>();

export const connectGeneralChat: WebsocketRequestHandler = async (ws, req) => {
  const clients = rooms.get("general") || new Set<WebSocket>();
  rooms.set("general", clients);
  clients.add(ws);

  await initializeRoom("general");

  ws.on("message", async (msg: string) => {
    try {
      const message: ChatMessage = JSON.parse(msg.toString());

      await saveMessage("general", message);

      // Broadcast to all clients in general chat
      clients.forEach((client) => {
        if (client !== ws && client.readyState === WebSocket.OPEN) {
          client.send(JSON.stringify(message));
        }
      });
    } catch (error) {
      console.error("Error processing message:", error);
    }
  });

  ws.on("close", () => {
    clients.delete(ws);
    if (clients.size === 0) {
      rooms.delete("general");
    }
  });
};

export const connectPrivateChat: WebsocketRequestHandler = async (ws, req) => {
  const roomId = req.params.privateChat;

  const clients = rooms.get(roomId) || new Set<WebSocket>();
  rooms.set(roomId, clients);
  clients.add(ws);

  await initializeRoom(roomId);

  ws.on("message", async (msg: string) => {
    try {
      const message: ChatMessage = JSON.parse(msg.toString());

      await saveMessage(roomId, message);

      // Broadcast to all clients in the private room
      clients.forEach((client) => {
        if (client !== ws && client.readyState === WebSocket.OPEN) {
          client.send(JSON.stringify(message));
        }
      });
    } catch (error) {
      console.error("Error processing message:", error);
    }
  });

  ws.on("close", () => {
    clients.delete(ws);
    if (clients.size === 0) {
      rooms.delete(roomId);
    }
  });
};
