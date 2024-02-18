package com.gliesestudio.jwt.dto;

import com.gliesestudio.jwt.exception.GlobalExceptionHandler;
import lombok.Data;

/**
 * Standard error response used by {@link GlobalExceptionHandler}
 *
 * @author MazidulIslam
 * @since 09-12-2023
 */
@Data
public class ErrorResponse {

    private Long timestamp = System.currentTimeMillis();
    private Integer statusCode;
    private String error;
    private String path;

}