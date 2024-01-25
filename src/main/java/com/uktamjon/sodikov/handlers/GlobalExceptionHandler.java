package com.uktamjon.sodikov.handlers;

import com.uktamjon.sodikov.dtos.AppErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<AppErrorDTO> usernameNotFoundException(HttpServletRequest request, UsernameNotFoundException e) {
        String errorBody = e.getMessage();
        String errorPath = request.getRequestURI();
        AppErrorDTO errorDTO = new AppErrorDTO(errorPath, null, errorBody, 500);
        return ResponseEntity.status(500).body(errorDTO);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AppErrorDTO> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String errorMessage = "Internal Server Error";
        String errorBody = e.getMessage();
        String errorPath = request.getRequestURI();
        AppErrorDTO errorDTO = new AppErrorDTO(errorPath, errorMessage, errorBody, 500);
        return ResponseEntity.status(500).body(errorDTO);
    }


}
