package com.jeykym.pot.dto;

import jakarta.validation.constraints.NotNull;

public record CreatePlayerRequest(
        @NotNull
        String name
) {
}
