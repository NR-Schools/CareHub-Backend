package com.it120p.carehub.exceptions;

public class PermissionException extends BaseException {
    public PermissionException(String type) {
        super("Action cannot be done due to missing permission(s)");
        this.setExceptionType(type);
        this.setStatusCode(401);
    }
}
