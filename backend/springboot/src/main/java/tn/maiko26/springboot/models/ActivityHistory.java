package tn.maiko26.springboot.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "activity_history")
public class ActivityHistory {
    @Id
    @Column(name = "history_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "email")
    private User user;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "resource_id")
    private String resourceId;

    @Column(name = "resource_type")
    private String resourceType;

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    private Date createdAt;

    @Column(name = "read_by", columnDefinition = "text[] default '{}'::text[]")
    @ElementCollection
    private List<String> readBy = new ArrayList<>();
}
