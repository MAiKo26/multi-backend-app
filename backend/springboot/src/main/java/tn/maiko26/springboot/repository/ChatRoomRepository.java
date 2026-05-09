package tn.maiko26.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.maiko26.springboot.model.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findById(String id);
}