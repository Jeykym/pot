package com.jeykym.pot.exception;

import com.jeykym.pot.dto.ErrorResponse;
import com.jeykym.pot.exception.customException.PlayerAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ResponseEntity<?> handlePlayerAlreadyExistException(PlayerAlreadyExistsException exception) {
        ErrorResponse playerAlreadyExists = new ErrorResponse("PLAYER_ALREADY_EXISTS", exception.getMessage());
        return new ResponseEntity<>(playerAlreadyExists, HttpStatus.BAD_REQUEST);
    }
}
