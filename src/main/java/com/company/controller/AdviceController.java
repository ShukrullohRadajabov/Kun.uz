package com.company.controller;

import com.company.exceptions.AppBadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.exceptions.MethodNotAllowedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class AdviceController {
    @ExceptionHandler({AppBadRequestException.class,
            ItemNotFoundException.class,
            MethodNotAllowedException.class})
    public ResponseEntity<String> handleException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
