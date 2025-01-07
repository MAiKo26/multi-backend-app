import WebSocket from "ws";
import {WebsocketRequestHandler} from "express-ws";

interface ChatMessage {
  text: string;
  sender: string;
  timestamp: number;
}

// Store rooms and their clients
const rooms = new Map<string, Set<WebSocket>>();

export const connectGeneralChat: WebsocketRequestHandler = (ws, req) => {
  const clients = rooms.get("general") || new Set<WebSocket>();
  rooms.set("general", clients);
  clients.add(ws);

  ws.on("message", (msg: string) => {
    try {
      const message: ChatMessage = JSON.parse(msg.toString());
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

export const connectPrivateChat: WebsocketRequestHandler = (ws, req) => {
  const roomId = req.params.privateChat;
  console.log(roomId);

  const clients = rooms.get(roomId) || new Set<WebSocket>();
  rooms.set(roomId, clients);
  clients.add(ws);

  ws.on("message", (msg: string) => {
    try {
      console.log(msg);

      const message: ChatMessage = JSON.parse(msg.toString());
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
