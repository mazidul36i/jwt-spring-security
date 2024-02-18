package com.gliesestudio.jwt.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * jwt token subject to store data in jwt token payload properly
 *
 * @author MazidulIslam
 * @since 10-12-2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JWTToken implements Serializable {

    private UUID userId;
    private String username;
    private String email;

}
