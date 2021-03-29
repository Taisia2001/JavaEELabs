package org.shutiak.lab9.controllers;
import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.security.SignatureException;
import org.shutiak.lab9.exceptions.IsbnException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<List<String>> handle(final MethodArgumentNotValidException ex) {
        final BindingResult bindingResult = ex.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        final List<String> errors = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return ResponseEntity.badRequest()
                .body(errors);
    }

    @ExceptionHandler({IsbnException.class})
    public ResponseEntity<String> handleIsbn(final IsbnException ex) {
        return ResponseEntity.badRequest()
                .body(ex.getMessage());
    }



}
