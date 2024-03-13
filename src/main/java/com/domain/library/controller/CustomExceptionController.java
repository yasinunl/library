package com.domain.library.controller;

import com.domain.library.dto.ExceptionResponseDTO;
import com.domain.library.exception.CustomBadException;
import com.domain.library.exception.CustomSuccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionController {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> customExceptionResponseEntity(CustomBadException exc){
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                exc.getMessage(),
                System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> customSuccessExceptionResponseEntity(CustomSuccessException exc){
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                exc.getMessage(),
                System.currentTimeMillis(),
                HttpStatus.OK.value()
        );
        return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handleError(Exception exc){
        ExceptionResponseDTO error = new ExceptionResponseDTO(
                exc.getMessage(),
                System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
