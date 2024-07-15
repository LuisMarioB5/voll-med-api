package com.bonidev.api.errors;

import com.bonidev.api.validaciones.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class HttpErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> error404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> error400(HandlerMethodValidationException e) {
        var errors = e.getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return new DatosErrorValidacion(fieldError.getField(), fieldError.getDefaultMessage());
                    }
                    return new DatosErrorValidacion("unknown", error.getDefaultMessage());
                })
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> errorValidationException(ValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private record DatosErrorValidacion(String campo, String mensajeError) {
        public DatosErrorValidacion(String campo, String mensajeError) {
            this.campo = campo;
            this.mensajeError = mensajeError;
        }
    }
}
