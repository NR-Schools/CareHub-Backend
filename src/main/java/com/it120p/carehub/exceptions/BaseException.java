package com.it120p.carehub.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends Exception {
    private String exceptionType;
    private int statusCode;
}
