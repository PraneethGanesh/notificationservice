package com.praneeth.notificationservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // -------------------------------------------------------------------------
    // 400 – Validation failures (field-level)
    // -------------------------------------------------------------------------

    /**
     * Handles @Valid / @Validated constraint violations on request bodies.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<ErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new ErrorResponse.FieldError(
                        fe.getField(),
                        fe.getRejectedValue() != null ? fe.getRejectedValue().toString() : "null",
                        fe.getDefaultMessage()
                ))
                .collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                "One or more fields failed validation. See 'fieldErrors' for details.",
                request.getRequestURI()
        );
        error.setFieldErrors(fieldErrors);

        log.warn("Validation failed for request [{}]: {}", request.getRequestURI(), fieldErrors);
        return ResponseEntity.badRequest().body(error);
    }

    // -------------------------------------------------------------------------
    // 400 – Malformed / unreadable JSON body
    // -------------------------------------------------------------------------

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableBody(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        log.warn("Malformed request body for [{}]: {}", request.getRequestURI(), ex.getMessage());

        return ResponseEntity.badRequest().body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Malformed Request Body",
                "The request body is missing or contains invalid JSON.",
                request.getRequestURI()
        ));
    }

    // -------------------------------------------------------------------------
    // 404 – No route found
    // -------------------------------------------------------------------------

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            NoHandlerFoundException ex,
            HttpServletRequest request) {

        log.warn("No handler found for [{}] {}", ex.getHttpMethod(), ex.getRequestURL());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                "The requested resource was not found: " + request.getRequestURI(),
                request.getRequestURI()
        ));
    }

    // -------------------------------------------------------------------------
    // 405 – Wrong HTTP method
    // -------------------------------------------------------------------------

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {

        log.warn("Method not supported: {} {}", ex.getMethod(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Method Not Allowed",
                ex.getMethod() + " is not supported for this endpoint.",
                request.getRequestURI()
        ));
    }

    // -------------------------------------------------------------------------
    // 500 – Catch-all for unexpected exceptions
    // -------------------------------------------------------------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Unhandled exception for request [{}]", request.getRequestURI(), ex);

        return ResponseEntity.internalServerError().body(new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred. Please try again later.",
                request.getRequestURI()
        ));
    }
}
