package com.jeykym.pot.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false, check = @CheckConstraint(constraint = "trim(name) <> ''"))
    private String name;

    @SuppressWarnings("unused")
    protected Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
