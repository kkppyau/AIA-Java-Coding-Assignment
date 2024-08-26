package com.alanyau.aia_coding_assignment.handler;

import com.alanyau.aia_coding_assignment.dto.response.ApiResponseDTO;
import com.alanyau.aia_coding_assignment.exception.BookAlreadyExistsException;
import com.alanyau.aia_coding_assignment.exception.BookNotFoundException;
import com.alanyau.aia_coding_assignment.exception.UnknownException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> BookNotFoundExceptionHandler(BookNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponseDTO<>(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(value = BookAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDTO<String>> BookAlreadyExistsExceptionHandler(BookAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ApiResponseDTO<>(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    @ExceptionHandler(value = UnknownException.class)
    public ResponseEntity<ApiResponseDTO<String>> UnknownExceptionHandler(UnknownException e) {
        return ResponseEntity.internalServerError().body(
                new ApiResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<String>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<String> errorMessage = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.add(error.getDefaultMessage());
        });

        Collections.sort(errorMessage);

        return ResponseEntity.badRequest().body(
                new ApiResponseDTO<>(HttpStatus.BAD_REQUEST.value(), errorMessage.toString()));
    }

}