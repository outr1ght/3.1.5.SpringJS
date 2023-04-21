package com.spring_security_app.spring_security_firstapp.util;

public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException(String message) {
        super(message);
    }
}
