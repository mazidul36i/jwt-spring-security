package com.gliesestudio.jwt.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Default acknowledgement response dto for any save/update request
 *
 * @author MazidulIslam
 * @since 12-12-2023
 */
@Data
@Builder
public class ResponseAcknowledgementDto {

    private Boolean isAcknowledge;
    private String message;
    private String errorMessage;

}
