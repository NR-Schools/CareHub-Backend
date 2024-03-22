package com.it120p.carehub.exceptions;

public class EmailAlreadyExistException extends BaseException {
    public EmailAlreadyExistException() {
        super("Email Already Exist!");
        this.setExceptionType("Email");
        this.setStatusCode(409);
    }
}
