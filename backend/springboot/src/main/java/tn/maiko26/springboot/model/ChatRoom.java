package tn.maiko26.springboot.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @Column(name = "room_id")
    private String id;

    @Column(name = "type")
    private String type;

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public ChatRoom() {}

    public ChatRoom(String id, String type) {
        this.id = id;
        this.type = type;
        this.createdAt = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}