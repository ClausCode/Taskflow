package ru.clauscode.taskflow;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionBody> handleGenericException(Exception e) {
        log.error("Server Exception: ", e);
        return ResponseEntity.status(500).body(new ExceptionBody(500, "Server Error"));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionBody> handleNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(404).body(new ExceptionBody(404, e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionBody> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(new ExceptionBody(400, e.getMessage()));
    }

    public record ExceptionBody(
            int code,
            String message
    ) {
    }
}

