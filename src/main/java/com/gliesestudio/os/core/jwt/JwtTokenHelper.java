package com.gliesestudio.os.core.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gliesestudio.os.core.dto.auth.AuthenticationResponse;
import com.gliesestudio.os.core.dto.auth.JwtTokenSubject;
import com.gliesestudio.os.core.model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * Jwt token helper to help with token creation, validation and authentication
 *
 * @author MazidulIslam
 * @since 09-12-2023
 */
@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 1000L; // 5 minutes
    public static final long JWT_REFRESH_TOKEN_VALIDITY = 30 * 24 * 60 * 60 * 1000L; // 30 days

    @Value("${JWT_SECRET_KEY:NjQzQUQyQUMtNEYwMi00NURGLUJBMjMtQkY3MzNCOTI3RDBG}")
    private String SECRET_KEY;

    @Value("${jwt.issuer:open-solutions}")
    private String issuer;

    /**
     * Generate jwt and jwt refresh token together
     *
     * @param user {@link UserModel}
     * @return {@link AuthenticationResponse} having jwt and jwt refresh token
     * @throws JsonProcessingException may throw if anything goes wrong with jwt subject creation
     */
    public final AuthenticationResponse generateCombinedJwtToken(UserModel user) throws JsonProcessingException {
        final String jwtSubject = generateJwtSubject(user);
        final String jwtToken = generateToken(jwtSubject);
        final String jwtRefreshToken = generateRefreshToken(jwtSubject);

        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .jwtRefreshToken(jwtRefreshToken)
                .build();
    }

    /**
     * Generate jwt token using jwt subject as json string
     *
     * @param jwtSubject jwt token in json string
     * @return {@link String} jwt token
     */
    private String generateToken(String jwtSubject) {
        return generateToken(jwtSubject, JWT_TOKEN_VALIDITY);
    }

    /**
     * Generate refresh jwt refresh token with having 30 days of expiration
     *
     * @param subject {@link String} jwt token subject. <br>It must contain username to create new token out of it
     * @return {@link String} jwt refresh token
     */
    private String generateRefreshToken(String subject) {
        return generateToken(subject, JWT_REFRESH_TOKEN_VALIDITY);
    }

    /**
     * Main jwt token generation method which takes subject in json string
     *
     * @param subject       valid json string as per {@link JwtTokenSubject}
     * @param tokenValidity token validity time in milliseconds
     * @return {@link String} jwt token
     */
    public final String generateToken(String subject, Long tokenValidity) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .setIssuer(issuer)
                .compact();
    }

    /**
     * Generate jwt token subject from user details
     *
     * @param userModel {@link UserModel}
     * @return {@link String} jwt token subject in json string
     * @throws JsonProcessingException if any error encounters while converting {@link JwtTokenSubject} to json string
     */
    public final String generateJwtSubject(UserModel userModel) throws JsonProcessingException {
        JwtTokenSubject jwtSubject = JwtTokenSubject.builder()
                .userId(userModel.getUserId())
                .username(userModel.getUsername())
                .email(userModel.getEmail())
                .build();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(jwtSubject);
    }

    /**
     * Extract username from jwt token
     *
     * @param token valid jwt token in {@link String}
     * @return authenticated username in {@link String}
     */
    public final String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract subject from {@link String} jwt token
     *
     * @param token {@link String} valid jwt token
     * @return jwt token subject in {@link String}
     */
    public final String getSubject(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    /**
     * Extract subject from {@link String} jwt token
     *
     * @param token {@link String} valid jwt token
     * @return {@link JwtTokenSubject} extracted from jwt token
     * @throws JsonProcessingException if any error encounters while converting json string subject to {@link JwtTokenSubject}
     */
    public final JwtTokenSubject getTokenSubject(String token) throws JsonProcessingException {
        String subject = getSubject(token);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(subject, JwtTokenSubject.class);
    }

    /**
     * Checks token's validation as if the user exists and not expired
     *
     * @param token       {@link String} valid jwt token
     * @param userDetails Spring security internal {@link UserDetails} or it's extended {@link UserModel}
     * @return boolean - true if the token is valid else false
     */
    public final boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Checks if the provided jwt token has expired
     *
     * @param token {@link String} valid jwt token
     * @return boolean - true if token has expired else false
     */
    public final boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    /**
     * Extract token expiration date
     *
     * @param token {@link String} valid jwt token
     * @return {@link Date} of jwt token expiration
     */
    public final Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract jwt token claims that includes authenticated username, expiration date, subject etc.
     *
     * @param token          {@link String} valid jwt token
     * @param claimsResolver {@link Function}
     * @param <T>            returning object
     * @return {@link Claims} in the requested object {@link <T>}
     */
    public final <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extract all claims from provided jwt token.
     *
     * @param token {@link String} valid jwt token
     * @return {@link Claims} all claims on the jwt token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Decoded and encrypted sign in key from the SECRET_KEY
     *
     * @return {@link Key} that is used to sign a jwt token
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}