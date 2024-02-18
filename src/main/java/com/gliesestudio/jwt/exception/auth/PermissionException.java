package com.gliesestudio.jwt.exception.auth;

import java.io.IOException;

/**
 * This exception should be thrown when a request tries to access a data which is restricted.
 *
 * @author MazidulIslam
 * @since 09-12-2023
 */
public class PermissionException extends IOException {

    public PermissionException() {
        super();
    }

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionException(Throwable cause) {
        super(cause);
    }
}
