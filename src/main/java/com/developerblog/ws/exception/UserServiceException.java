package com.developerblog.ws.exception;

import java.io.Serial;

public class UserServiceException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 4851069111288925447L;

    public UserServiceException(String message) {
        super(message);
    }
}
