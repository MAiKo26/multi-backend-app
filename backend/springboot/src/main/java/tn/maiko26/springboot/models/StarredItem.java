package tn.maiko26.springboot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "starred_items")
public class StarredItem {
    @Id
    @Column(name = "star_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "email")
    private User user;

    @Column(name = "item_type", nullable = false)
    private String itemType;

    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    private Date createdAt;
}


