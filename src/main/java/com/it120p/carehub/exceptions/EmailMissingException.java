package com.it120p.carehub.exceptions;

public class EmailMissingException extends BaseException {
    public EmailMissingException() {
        super("Email is Missing!");
        this.setExceptionType("Email");
        this.setStatusCode(409);
    }
}
