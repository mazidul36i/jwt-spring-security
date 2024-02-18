package com.gliesestudio.os.core.controller.auth;

import com.gliesestudio.os.core.dto.auth.AuthenticationResponse;
import com.gliesestudio.os.core.dto.auth.RegisterRequest;
import com.gliesestudio.os.core.exception.auth.AuthenticationException;
import com.gliesestudio.os.core.service.auth.AuthenticationServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller to provide endpoints for registration and login
 *
 * @author MazidulIslam
 * @since 09-12-2023
 */
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationServiceImpl authService;

    /**
     * Registration API endpoint (/register) to register a user
     *
     * @param request it takes valid {@link RegisterRequest}
     * @return {@link ResponseEntity} with {@link AuthenticationResponse} having jwtToken
     * @throws AuthenticationException in case of any validation or authentication error
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) throws AuthenticationException {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Login and token refresh API endpoints (/login, /refresh) to login and refresh jwt token
     *
     * @param authorization request header in the API request having Basic auth or Bearer token
     * @return {@link ResponseEntity} with {@link AuthenticationResponse} having jwtToken
     * @throws AuthenticationException in case of any validation or authentication error
     */
    @PostMapping({"/login", "/refresh"})
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestHeader(required = false, value = "Authorization") String authorization) throws AuthenticationException {
        if (!StringUtils.hasText(authorization)) {
            throw new AuthenticationException("Required credentials for authentication!");
        }
        return ResponseEntity.ok(authService.authenticate(authorization));
    }

    /**
     * Ping-pong request API to check request without authentication
     *
     * @return {@link String} with value "Pong"
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        log.info("Ping from client");
        return ResponseEntity.ok("Pong");
    }

}
