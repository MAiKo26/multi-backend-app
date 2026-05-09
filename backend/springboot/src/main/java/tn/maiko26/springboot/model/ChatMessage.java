package tn.maiko26.springboot.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer id;

    @Column(name = "room_id", nullable = false)
    private String roomId;

    @Column(name = "sender_id")
    private String senderId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "read_by", columnDefinition = "TEXT")
    private String readBy;

    public ChatMessage() {}

    public ChatMessage(String roomId, String senderId, String content) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.content = content;
        this.createdAt = new Date();
        this.readBy = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getReadBy() {
        return readBy;
    }

    public void setReadBy(String readBy) {
        this.readBy = readBy;
    }
}