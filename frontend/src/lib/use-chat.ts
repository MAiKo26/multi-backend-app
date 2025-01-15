import { toast } from "@/hooks/use-toast";
import useStore from "@/store/useStore";
import { useEffect, useState, useCallback } from "react";

interface Message {
  roomId: string;
  id?: number;
  readBy?: string[];
  senderId: string;
  createdAt: string;
  content: string;
}

export function useChat({ room }: { room: string }) {
  const { currentUser, token } = useStore();
  const username = currentUser!.name;
  const email = currentUser!.email;
  const [rooms, setRooms] = useState<Record<string, Message[]>>({});
  const [socket, setSocket] = useState<WebSocket | null>(null);
  const [isConnected, setIsConnected] = useState(false);

  // Initialize room if it doesn't exist
  useEffect(() => {
    setRooms((prev) => ({
      ...prev,
      [room]: prev[room] || [],
    }));
  }, [room]);

  useEffect(() => {
    const ws = new WebSocket(`ws://localhost:3636/chat/${room}`);

    ws.onopen = async () => {
      setIsConnected(true);

      const response = await fetch(
        `http://localhost:3636/chat/history/${room ? room : "general"}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );

      if (response.status == 200) {
        const history = await response.json();

        setRooms((prev) => ({
          ...prev,
          [room]: history,
        }));
      } else {
        toast({
          title: "Failed to load previous chat :(",
          variant: "destructive",
        });
      }
    };

    ws.onmessage = (event) => {
      const message = JSON.parse(event.data);

      setRooms((prev) => ({
        ...prev,
        [room]: [...(prev[room] || []), message],
      }));
    };

    ws.onclose = () => {
      setIsConnected(false);
    };

    setSocket(ws);

    return () => {
      ws.close();
    };
  }, [room]);

  const sendMessage = useCallback(
    (content: string) => {
      if (socket?.readyState === WebSocket.OPEN) {
        const message: Message = {
          content,
          roomId: room,
          senderId: email,
          createdAt: new Date().toISOString(),
        };

        socket.send(JSON.stringify(message));
        setRooms((prev) => ({
          ...prev,
          [room]: [...(prev[room] || []), message],
        }));
      }
    },
    [socket, username, email, room],
  );

  return {
    messages: rooms[room] || [],
    sendMessage,
    isConnected,
  };
}
