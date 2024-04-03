package com.it120p.carehub.exceptions;

public class MissingException extends BaseException {
    public MissingException(String type) {
        super(type + " is Missing!");
        this.setExceptionType(type);
        this.setStatusCode(404);
    }
}
