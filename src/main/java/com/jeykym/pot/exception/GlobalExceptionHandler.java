package com.jeykym.pot.exception;

import com.jeykym.pot.dto.ErrorResponse;
import com.jeykym.pot.exception.customException.PlayerAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PotException.class)
    public ResponseEntity<?> handlePotException(PotException exception) {
        ErrorResponse potException = new ErrorResponse(exception.getCode(), exception.getMessage());
        return new ResponseEntity<>(potException, exception.getStatus());
    }
}
