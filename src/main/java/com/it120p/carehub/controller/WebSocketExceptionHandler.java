package com.it120p.carehub.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.socket.WebSocketSession;

@ControllerAdvice
public class WebSocketExceptionHandler {
    
    @ExceptionHandler(AuthenticationException.class)
    public void handleAuthenticationException(AuthenticationException ex, WebSocketSession session) {
        
        System.out.println(ex.getMessage());

        try {
            session.close();
        } catch (Exception e) {
            // Handle the exception
        }
    }
}
