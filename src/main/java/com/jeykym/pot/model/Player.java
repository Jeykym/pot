package com.jeykym.pot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false, check = @CheckConstraint(constraint = "trim(name) <> ''"))
    private String name;

    @SuppressWarnings("unused")
    protected Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
