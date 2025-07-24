package com.plantcommunity.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_favorites", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "item_id"})
})
@EntityListeners(AuditingEntityListener.class)
public class ExchangeFavorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private ExchangeItem item;

    @CreatedDate
    private LocalDateTime createdAt;

    // Constructors
    public ExchangeFavorite() {}

    public ExchangeFavorite(User user, ExchangeItem item) {
        this.user = user;
        this.item = item;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public ExchangeItem getItem() { return item; }
    public void setItem(ExchangeItem item) { this.item = item; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
