package com.jeykym.pot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ParticipationId implements Serializable {

    @Column(name = "player_id")
    private UUID playerId;

    @Column(name = "game_id")
    private UUID gameId;

    @SuppressWarnings("unused")
    public ParticipationId() {}

    ParticipationId(UUID playerId, UUID gameId) {
        this.playerId = playerId;
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParticipationId that = (ParticipationId) o;

        return Objects.equals(playerId, that.playerId) &&
                Objects.equals(gameId, that.gameId);
    }


    @Override
    public int hashCode() {
        return Objects.hash(playerId, gameId);
    }
}
