package com.jeykym.pot.exception.customException;

import com.jeykym.pot.exception.PotException;
import org.springframework.http.HttpStatus;

public class InvalidFieldException extends PotException {

    public InvalidFieldException(String message) {
        super(HttpStatus.BAD_REQUEST, "INVALID_FIELD", message);
    }

    public InvalidFieldException(String errorCode, String message) {
        super(HttpStatus.BAD_REQUEST, errorCode, message);
    }
}