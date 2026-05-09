package tn.maiko26.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.maiko26.springboot.model.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

    @Query("SELECT m FROM ChatMessage m WHERE m.roomId = :roomId ORDER BY m.createdAt ASC")
    List<ChatMessage> findByRoomIdOrderByCreatedAtAsc(@Param("roomId") String roomId);
}