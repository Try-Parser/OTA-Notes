package com.ota.note.handler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Map;

/***
 * MethodException for request body.
 */
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(
            Map.of(
                "success", false,
                "errors", ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
            ),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> notReadable(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(
            Map.of(
                "success", false,
                "errors", List.of("Not readable.")
            ),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> typeMissMatch(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>(
            Map.of(
                "success", false,
                "errors", List.of("Invalid format.")
            ),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> notFound(NoResourceFoundException e) {
        return new ResponseEntity<>(
            Map.of(
                "success", false,
                "errors", List.of("Are you lost ? resources somewhat not found.")
            ),
            HttpStatus.BAD_REQUEST
        );
    }
}
