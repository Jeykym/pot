package com.jeykym.pot.exception.customException;

import com.jeykym.pot.exception.PotException;
import org.springframework.http.HttpStatus;

public class InvalidFieldException extends PotException {
    public InvalidFieldException() {
        super(
                HttpStatus.BAD_REQUEST,
                "PLAYER_NAME_IS_BLANK",
                "Player's name cannot be blank"
        );
    }
}
