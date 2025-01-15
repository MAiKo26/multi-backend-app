import { useState } from "react";
import { Send } from "lucide-react";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { ScrollArea } from "@/components/ui/scroll-area";
import { useChat } from "@/lib/use-chat";

export default function Chat() {
  const [messageInput, setMessageInput] = useState("");
  const [isSending, setIsSending] = useState(false);
  const { isConnected, messages, sendMessage } = useChat({ room: "" });

  const handleSend = async () => {
    if (!messageInput.trim() || isSending) return;

    try {
      setIsSending(true);
      await sendMessage(messageInput);
      setMessageInput("");
    } catch (error) {
      console.error("Failed to send message:", error);
    } finally {
      setIsSending(false);
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      handleSend();
    }
  };

  return (
    <Card className="h-[calc(100vh-2rem)]">
      <CardHeader>
        <CardTitle>General Chat</CardTitle>
      </CardHeader>
      <CardContent className="flex h-[calc(100%-8rem)] flex-col gap-4">
        <ScrollArea className="flex-1 rounded-lg border p-4">
          <div className="space-y-4">
            {messages.map((message, index) => (
              <div key={index} className="flex items-start gap-4">
                <Avatar>
                  <AvatarImage src={`/avatars/${message.senderId}.jpg`} />
                  <AvatarFallback>{message.senderId[0]}</AvatarFallback>
                </Avatar>
                <div className="grid gap-1">
                  <div className="flex items-center gap-2">
                    <span className="font-semibold">{message.senderId}</span>
                    <span className="text-xs text-muted-foreground">
                      {new Date(message.createdAt).toLocaleTimeString()}
                    </span>
                  </div>
                  <p className="text-sm">{message.content}</p>
                </div>
              </div>
            ))}
          </div>
        </ScrollArea>

        <div className="flex items-center gap-2">
          <Input
            placeholder="Type your message..."
            value={messageInput}
            onChange={(e) => setMessageInput(e.target.value)}
            onKeyPress={handleKeyPress}
            disabled={isSending || !isConnected}
          />
          <Button
            size="icon"
            onClick={handleSend}
            disabled={isSending || !messageInput.trim() || !isConnected}
          >
            <Send className={`h-4 w-4 ${isSending ? "animate-pulse" : ""}`} />
          </Button>
        </div>
      </CardContent>
    </Card>
  );
}
