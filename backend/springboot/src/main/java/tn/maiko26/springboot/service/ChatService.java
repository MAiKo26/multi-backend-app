package tn.maiko26.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.maiko26.springboot.model.ChatMessage;
import tn.maiko26.springboot.model.ChatRoom;
import tn.maiko26.springboot.repository.ChatMessageRepository;
import tn.maiko26.springboot.repository.ChatRoomRepository;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public void initializeRoom(String roomId) {
        if (!chatRoomRepository.existsById(roomId)) {
            ChatRoom room = new ChatRoom(roomId, "private");
            chatRoomRepository.save(room);
        }
    }

    public void initializeGeneralRoom() {
        if (!chatRoomRepository.existsById("general")) {
            ChatRoom room = new ChatRoom("general", "general");
            chatRoomRepository.save(room);
        }
    }

    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getMessageHistory(String roomId) {
        return chatMessageRepository.findByRoomIdOrderByCreatedAtAsc(roomId);
    }

    public ChatMessage createMessage(String roomId, String senderId, String content) {
        ChatMessage message = new ChatMessage(roomId, senderId, content);
        return chatMessageRepository.save(message);
    }
}