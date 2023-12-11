package com.gliesestudio.os.core.service.auth;

import com.gliesestudio.os.core.dto.auth.AuthenticationResponse;
import com.gliesestudio.os.core.dto.auth.RegisterRequest;
import com.gliesestudio.os.core.exception.auth.AuthenticationException;

/**
 * Service interface to define all the abstracted methods required in authentication
 *
 * @author MazidulIslam
 * @implNote Implementation service class {@link AuthenticationServiceImpl}
 * @since 10-12-2023
 */
public interface AuthenticationService {

    /**
     * Register a new user into the application with valid & unique user details
     *
     * @param request valid user details with unique email and username
     * @return {@link AuthenticationResponse} with jwt and refresh jwt token
     * @throws AuthenticationException if any exception comes while registration
     */
    AuthenticationResponse register(RegisterRequest request) throws AuthenticationException;

    /**
     * Authenticate user with basic auth (username & password) in case of login or bearer token while refreshing the token
     *
     * @param authorization request header having authentication details
     * @return {@link AuthenticationResponse} with jwt and refresh jwt token
     * @throws AuthenticationException if any exception comes while authentication
     */
    AuthenticationResponse authenticate(String authorization) throws AuthenticationException;


}
