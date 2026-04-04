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

    @Column(name = "buy_in")
    private Integer buyIn;

    private Integer result;

    @SuppressWarnings("unused")
    public Participation() {}

    public Participation(Player player, Game game) {
        this.player = player;
        this.game = game;
        this.id = new ParticipationId(player.getId(), game.getId());
    }

    public Long getPlayerId() {
        return player.getId();
    }

    public Long getGameId() {
        return game.getId();
    }
}
