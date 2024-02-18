package com.gliesestudio.jwt.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API response after successful authentication with authenticated jwt token
 *
 * @author MazidulIslam
 * @since 10-12-2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String jwtToken;
    private String jwtRefreshToken;

}