package com.clinic.agenda.api;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.*;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> notFound(EntityNotFoundException ex){ return error(HttpStatus.NOT_FOUND, ex.getMessage()); }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> badRequest(IllegalArgumentException ex){ return error(HttpStatus.BAD_REQUEST, ex.getMessage()); }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validation(MethodArgumentNotValidException ex){
        String msg = ex.getBindingResult().getAllErrors().stream().findFirst()
                .map(e -> e.getDefaultMessage()).orElse("Validación inválida");
        return error(HttpStatus.BAD_REQUEST, msg);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> generic(Exception ex){ return error(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado"); }

    private ResponseEntity<Map<String,Object>> error(HttpStatus st, String msg){
        Map<String,Object> body = new HashMap<>();
        body.put("timestamp", OffsetDateTime.now());
        body.put("status", st.value());
        body.put("error", st.getReasonPhrase());
        body.put("message", msg);
        return ResponseEntity.status(st).body(body);
    }
}