package com.jeykym.pot.dto;

import com.jeykym.pot.model.Player;

import java.util.UUID;

public record PlayerDTO(
        UUID id,
        String name
) {
    public static PlayerDTO from(Player player) {
        return new PlayerDTO(player.getId(), player.getName());
    }
}
