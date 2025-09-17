package com.financetracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Comment;


import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("Unique record identifier (auto-increment)")
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    @Comment("Public unique identifier for API")
    private String uuid = UUID.randomUUID().toString();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Comment("Record creation timestamp")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Comment("Record last update timestamp")
    private LocalDateTime updatedAt;

    @Version
    @Comment("Record version for optimistic locking")
    private Long version;

    @PrePersist
    protected void onCreate() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
