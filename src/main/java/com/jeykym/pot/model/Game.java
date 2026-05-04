package com.jeykym.pot.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Instant playedAt;

    @Column
    private UUID authorPlayerId;

    public Game(Instant playedAt) {
        this.playedAt = playedAt;
    }

    @SuppressWarnings("unused")
    public Game() {

    }

    public UUID getId() {
        return id;
    }

    public Instant getPlayedAt() {
        return playedAt;
    }
}
