package com.gliesestudio.jwt.exception;

import com.gliesestudio.jwt.dto.ErrorResponse;
import com.gliesestudio.jwt.exception.auth.PermissionException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Objects;

/**
 * Global exception handler to respond to any exception with a standard dto
 *
 * @author MazidulIslam
 * @since 09-12-2023
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * When an API request comes which doesn't exist
     *
     * @param e       {@link NoHandlerFoundException}
     * @param request {@link WebRequest}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NoHandlerFoundException e, WebRequest request) {
        ErrorResponse response = generateErrorMsgDto(e, request, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * When a request comes with wrong API request method
     *
     * @param e       {@link HttpRequestMethodNotSupportedException}
     * @param request {@link WebRequest}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, WebRequest request) {
        ErrorResponse response = generateErrorMsgDto(e, request, HttpStatus.METHOD_NOT_ALLOWED);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    /**
     * When a request comes with wrong API arguments
     *
     * @param e       {@link MethodArgumentNotValidException}
     * @param request {@link WebRequest}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, WebRequest request) {
        ErrorResponse messageDto = generateErrorMsgDto(e, request, HttpStatus.BAD_REQUEST);
        messageDto.setError(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
        return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * When a request comes with all the request parameters but with wrong datatype
     *
     * @param e       {@link MethodArgumentTypeMismatchException}
     * @param request {@link WebRequest}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e, WebRequest request) {
        ErrorResponse messageDto = generateErrorMsgDto(e, request, HttpStatus.BAD_REQUEST);
        messageDto.setError("Type mismatch! Passed invalid parameter");
        return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * When a request comes for a resource and it is not found
     *
     * @param e       {@link ResourceNotFoundException}
     * @param request {@link WebRequest}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e, WebRequest request) {
        ErrorResponse response = generateErrorMsgDto(e, request, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * When a request comes to set a data parameter which already exists and duplicate is not allowed
     *
     * @param e       {@link InvalidParameterException}
     * @param request {@link WebRequest}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorResponse> invalidParameterExceptionHandler(InvalidParameterException e, WebRequest request) {
        ErrorResponse messageDto = generateErrorMsgDto(e, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
    }

    /**
     * When a request to access something which is not allowed to access with the permission level a the user has
     *
     * @param e       {@link PermissionException}
     * @param request {@link WebRequest}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<ErrorResponse> permissionExceptionHandler(PermissionException e, WebRequest request) {
        ErrorResponse messageDto = generateErrorMsgDto(e, request, HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(messageDto, HttpStatus.FORBIDDEN);
    }

    /**
     * When any jwt token related error comes like token validation, parsing etc
     *
     * @param e       {@link JwtException}
     * @param request {@link WebRequest}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> authenticationExceptionHandler(JwtException e, WebRequest request) {
        ErrorResponse messageDto = generateErrorMsgDto(e, request, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(messageDto, HttpStatus.UNAUTHORIZED);
    }

    /**
     * When a request comes with expired jwt token
     *
     * @param e       {@link ExpiredJwtException}
     * @param request {@link WebRequest}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> expiredJwtExceptionHandler(ExpiredJwtException e, WebRequest request) {
        ErrorResponse messageDto = generateErrorMsgDto(e, request, HttpStatus.UNAUTHORIZED);
        messageDto.setError("JWT token expired! Please login again to continue.");
        return new ResponseEntity<>(messageDto, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Any runtime exception which encounters and no specific exception defined for that type
     *
     * @param e       {@link RuntimeException}
     * @param request {@link WebRequest}
     * @return {@link ErrorResponse}
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(RuntimeException e, WebRequest request) {
        ErrorResponse messageDto = generateErrorMsgDto(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(messageDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Generate standard {@link ErrorResponse}
     *
     * @param e       {@link Exception}
     * @param request {@link WebRequest}
     * @param status  {@link HttpStatus}
     * @return Generated {@link ErrorResponse} with the provided method parameters
     */
    private ErrorResponse generateErrorMsgDto(Exception e, WebRequest request, HttpStatus status) {
        ErrorResponse messageDto = new ErrorResponse();
        messageDto.setStatusCode(status.value());
        messageDto.setError(e.getMessage());
        messageDto.setPath(request.getDescription(false).replaceFirst("uri=", ""));
        return messageDto;
    }

}