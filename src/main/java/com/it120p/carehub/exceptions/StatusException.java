package com.it120p.carehub.exceptions;

public class StatusException extends BaseException {
    public StatusException(String type, String expected, String actual) {
        super(expected + " is expected but was " + actual);
        this.setExceptionType(type);
        this.setStatusCode(409);
    }
}
