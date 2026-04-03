package com.jeykym.pot.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Instant date;

    public Game(Instant date) {
        this.date = date;
    }

    @SuppressWarnings("unused")
    public Game() {

    }

    public Long getId() {
        return id;
    }

    public Instant getDate() {
        return date;
    }
}
