package com.it120p.carehub.exceptions;

public class AlreadyExistException extends BaseException {
    public AlreadyExistException(String type) {
        super(type + " Already Exist!");
        this.setExceptionType(type);
        this.setStatusCode(409);
    }
}
