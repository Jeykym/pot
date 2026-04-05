package com.jeykym.pot.model;

import jakarta.persistence.*;

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

    @Column(name = "buy_in", nullable = false)
    private Integer buyIn;

    @Column(name = "final_stack", nullable = false)
    private Integer finalStack;

    @SuppressWarnings("unused")
    public Participation() {}

    public Participation(Player player, Game game, Integer buyIn, Integer finalStack) {
        this.player = player;
        this.game = game;
        this.id = new ParticipationId(player.getId(), game.getId());
        this.buyIn = buyIn;
        this.finalStack = finalStack;

    }

    private void sta() {
    }

    public Long getPlayerId() {
        return player.getId();
    }

    public Long getGameId() {
        return game.getId();
    }

    public Integer getBuyIn() {
        return buyIn;
    }

    public Integer getFinalStack() {
        return finalStack;
    }
}
