package tn.maiko26.springboot.models.TBD;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.maiko26.springboot.models.User;

import java.util.Date;
import java.util.List;

//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Table(name = "chat_messages")
public class ChatMessage {
//    @Id
//    @Column(name = "message_id")
//    private String id;
//
//    @ManyToOne
//    @JoinColumn(name = "room_id")
//    private ChatRoom room;
//
//    @ManyToOne
//    @JoinColumn(name = "sender_id", referencedColumnName = "email")
//    private User sender;
//
//    @Column(name = "content", nullable = false)
//    private String content;
//
//    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
//    private Date createdAt;
//
//    @OneToMany
//    @JoinColumn(name = "message_id",referencedColumnName = "email")
//    private List<User> user ;
}
