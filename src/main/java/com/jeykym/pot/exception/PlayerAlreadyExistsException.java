package com.jeykym.pot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PlayerAlreadyExistsException extends RuntimeException {
    public PlayerAlreadyExistsException(String name) {
        super("Player with name '" + name + "' already exists");
    }
}