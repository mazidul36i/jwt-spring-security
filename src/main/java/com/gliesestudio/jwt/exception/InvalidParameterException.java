package com.gliesestudio.jwt.exception;

import java.io.IOException;

/**
 * This exception should be thrown when a request comes to set a data parameter which already exists and duplicate is not allowed
 *
 * @author MazidulIslam
 * @since 09-12-2023
 */
public class InvalidParameterException extends IOException {

    public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParameterException(Throwable cause) {
        super(cause);
    }
}
