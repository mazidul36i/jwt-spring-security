package com.gliesestudio.jwt.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Entry point for a jwt authentication
 *
 * @author MazidulIslam
 * @since 09-12-2023
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Handle jwt authentication exception
     *
     * @param request       {@link HttpServletRequest}
     * @param response      {@link HttpServletResponse}
     * @param authException {@link AuthenticationException}
     * @throws IOException handle any exception that can be thrown by this method like {@link ServletException}
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied");
    }

}