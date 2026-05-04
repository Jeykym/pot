package com.jeykym.pot.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Participation {
    @EmbeddedId
    private ParticipationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playerId")
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "buy_in", nullable = false, check = @CheckConstraint(constraint = "buy_in >= 0"))
    private Integer buyIn;

    @Column(name = "final_stack", nullable = false, check = @CheckConstraint(constraint = "final_stack >= 0"))
    private Integer finalStack;

    @SuppressWarnings("unused")
    public Participation() {}

    public Participation(Player player, Game game, Integer buyIn, Integer finalStack) {
        this.player = player;
        this.game = game;
        this.id = new ParticipationId();
        this.buyIn = buyIn;
        this.finalStack = finalStack;

    }

    private void sta() {
    }

    public UUID getPlayerId() {
        return player.getId();
    }

    public UUID getGameId() {
        return game.getId();
    }

    public Integer getBuyIn() {
        return buyIn;
    }

    public Integer getFinalStack() {
        return finalStack;
    }
}
