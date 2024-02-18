package com.gliesestudio.os.core.dto;

import com.gliesestudio.os.core.exception.GlobalExceptionHandler;
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