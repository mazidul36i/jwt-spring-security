package com.gliesestudio.jwt.exception.auth;

import java.io.IOException;

/**
 * This exception should be thrown when any type of error encountered while authentication if no specific exception is defined.
 *
 * @author MazidulIslam
 * @since 09-12-2023
 */
public class AuthenticationException extends IOException {

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
