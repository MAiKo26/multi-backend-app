package tn.maiko26.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.maiko26.springboot.model.ChatMessage;
import tn.maiko26.springboot.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/chat")
@PreAuthorize("hasAnyRole('user','admin')")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/history/{roomId}")
    public ResponseEntity<List<ChatMessage>> getHistory(@PathVariable String roomId) {
        List<ChatMessage> messages = chatService.getMessageHistory(roomId);
        return ResponseEntity.ok(messages);
    }
}