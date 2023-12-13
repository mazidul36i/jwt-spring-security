package com.gliesestudio.os.core.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gliesestudio.os.core.dto.auth.AuthenticationResponse;
import com.gliesestudio.os.core.dto.auth.JWTToken;
import com.gliesestudio.os.core.dto.auth.RegisterRequest;
import com.gliesestudio.os.core.enums.UserRole;
import com.gliesestudio.os.core.exception.auth.AuthenticationException;
import com.gliesestudio.os.core.jwt.JwtTokenHelper;
import com.gliesestudio.os.core.model.UserModel;
import com.gliesestudio.os.core.model.UserRoleModel;
import com.gliesestudio.os.core.repository.UserRepository;
import com.gliesestudio.os.core.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.Base64;
import java.util.Optional;

/**
 * Implementation of the service interface {@link AuthenticationService}
 *
 * @author MazidulIslam
 * @since 10-12-2023
 */
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final static String AUTH_TYPE_BASIC = "Basic";
    private final static String AUTH_TYPE_BEARER = "Bearer";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) throws AuthenticationException {
        Optional<UserModel> checkUser = userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(request.getEmail(), request.getUsername());

        if (checkUser.isPresent() && checkUser.get().getEmail().equals(request.getEmail()))
            throw new InvalidParameterException("Another account is already registered with the given email!");
        else if (checkUser.isPresent())
            throw new InvalidParameterException("The username is not available, please try something else!");

        Long currentTimeInMillis = System.currentTimeMillis();
        UserModel user = UserModel.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .createdOn(currentTimeInMillis)
                .lastModified(currentTimeInMillis)
                .isAccountNonExpired(true) // default true
                .isAccountNonLocked(true) // default true
                .build();
        // Encrypt and save password & assign default USER role
        user.setPassword(request.getPassword());
        UserModel savedUser = userRepository.save(user);

        UserRoleModel userRole = UserRoleModel.builder()
                .userId(savedUser.getUserId())
                .role(UserRole.USER)
                .createdOn(currentTimeInMillis)
                .lastModified(currentTimeInMillis)
                .build();
        userRoleRepository.save(userRole);

        try {
            return jwtTokenHelper.generateCombinedJwtToken(user);
        } catch (JsonProcessingException e) {
            log.error("Exception in creating jwt token subject", e);
            throw new AuthenticationException("Exception in creating jwt token subject", e);
        }
    }

    @Override
    public AuthenticationResponse authenticate(String authorization) throws AuthenticationException {
        if (authorization.startsWith(AUTH_TYPE_BASIC)) {
            String[] usernameAndPassword = decodeBasicAuth(authorization);
            return basicAuthentication(usernameAndPassword[0], usernameAndPassword[1]);
        } else if (authorization.startsWith(AUTH_TYPE_BEARER)) {
            String bearerToken = authorization.split(" ")[1];
            return bearerTokenRefresh(bearerToken);
        } else {
            throw new AuthenticationException("Required credentials for authentication!");
        }
    }

    /**
     * Basic authentication with username and password
     *
     * @param username user's username or email
     * @param password user's password
     * @return {@link AuthenticationResponse} with jwt and refresh jwt token
     */
    private AuthenticationResponse basicAuthentication(String username, String password) {
        log.info("Basic authentication for username: [{}]", username);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserModel user = userRepository.findByEmailIgnoreCaseOrUsernameIgnoreCase(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username"));

        try {
            return jwtTokenHelper.generateCombinedJwtToken(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Authentication with bearer (jwt) token to refresh the main token
     *
     * @param bearerToken valid refresh token
     * @return {@link AuthenticationResponse} with jwt and refresh jwt token
     * @throws AuthenticationException if any exception comes while authentication
     */
    private AuthenticationResponse bearerTokenRefresh(String bearerToken) throws AuthenticationException {
        try {
            JWTToken jwtSubject = jwtTokenHelper.getTokenSubject(bearerToken);
            final Optional<UserModel> user = userRepository.findById(jwtSubject.getUserId());
            if (user.isEmpty()) {
                throw new AuthenticationException("User doesn't exists");
            }

            return jwtTokenHelper.generateCombinedJwtToken(user.get());
        } catch (JsonProcessingException e) {
            log.error("Exception in parsing JWT token", e);
            throw new AuthenticationException(e);
        }
    }

    /**
     * Decode Authorization header and fetch username and password
     *
     * @param authorization hashed auth header
     * @return [username, password]
     */
    private String[] decodeBasicAuth(String authorization) {
        String encodedCredentials = authorization.split(" ")[1];
        byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
        String decodedCredentials = new String(decodedBytes, StandardCharsets.UTF_8);
        return decodedCredentials.split(":");
    }
}
