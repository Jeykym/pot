package com.jeykym.pot.exception.customException;

import com.jeykym.pot.exception.PotException;
import org.springframework.http.HttpStatus;

public class PlayerAlreadyExistsException extends PotException {

    public PlayerAlreadyExistsException(String playerName) {
        super(
                HttpStatus.BAD_REQUEST,
                "PLAYER_ALREADY_EXISTS",
                "Player with name '" + playerName + "' already exists"
        );
    }
}