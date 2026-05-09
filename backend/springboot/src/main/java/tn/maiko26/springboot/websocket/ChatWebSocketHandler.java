package tn.maiko26.springboot.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import tn.maiko26.springboot.model.ChatMessage;
import tn.maiko26.springboot.service.ChatService;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uri = session.getUri() != null ? session.getUri().getPath() : "";
        String roomId = extractRoomId(uri);

        if (roomId == null) {
            roomId = "general";
        }

        Set<WebSocketSession> roomSessions = rooms.computeIfAbsent(roomId, k -> new CopyOnWriteArraySet<>());
        roomSessions.add(session);

        if (roomId.equals("general")) {
            chatService.initializeGeneralRoom();
        } else {
            chatService.initializeRoom(roomId);
        }

        session.getAttributes().put("roomId", roomId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        String roomId = (String) session.getAttributes().get("roomId");

        if (roomId == null) {
            return;
        }

        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        chatMessage.setRoomId(roomId);

        ChatMessage savedMessage = chatService.saveMessage(chatMessage);
        chatMessage.setId(savedMessage.getId());

        String messageJson = objectMapper.writeValueAsString(chatMessage);

        Set<WebSocketSession> roomSessions = rooms.get(roomId);
        if (roomSessions != null) {
            for (WebSocketSession client : roomSessions) {
                if (client.isOpen() && !c   lient.equals(session)) {
                    client.sendMessage(new TextMessage(messageJson));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = (String) session.getAttributes().get("roomId");
        if (roomId != null) {
            Set<WebSocketSession> roomSessions = rooms.get(roomId);
            if (roomSessions != null) {
                roomSessions.remove(session);
                if (roomSessions.isEmpty()) {
                    rooms.remove(roomId);
                }
            }
        }
    }

    private String extractRoomId(String uri) {
        if (uri == null || uri.isEmpty()) {
            return "general";
        }

        String[] parts = uri.split("/");
        for (int i = 0; i < parts.length; i++) {
            if ("chat".equals(parts[i]) && i + 1 < parts.length) {
                return parts[i + 1];
            }
        }
        return null;
    }
}