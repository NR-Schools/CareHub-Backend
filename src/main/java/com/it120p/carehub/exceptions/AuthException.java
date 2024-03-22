package com.it120p.carehub.exceptions;

public class AuthException extends BaseException {
    public AuthException() {
        super("Email or Password information is incorrect!");
        this.setExceptionType("Auth");
        this.setStatusCode(401);
    }
}
