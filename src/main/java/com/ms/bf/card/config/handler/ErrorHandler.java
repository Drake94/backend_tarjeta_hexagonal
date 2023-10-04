package com.ms.bf.card.config.handler;

import com.ms.bf.card.adapter.rest.exception.BadRequestRestClientException;
import com.ms.bf.card.adapter.rest.exception.EmptyOrNullBodyRestClientException;
import com.ms.bf.card.adapter.rest.exception.NonTargetRestClientException;
import com.ms.bf.card.adapter.rest.exception.NotFoundRestClientException;
import com.ms.bf.card.adapter.rest.exception.RestClientGenericException;
import com.ms.bf.card.adapter.rest.exception.TimeoutRestClientException;
import com.ms.bf.card.config.ErrorCode;
import com.ms.bf.card.config.exception.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    private static final String DEVELOP_PROFILE = "dev";
    private static final String LOCAL_PROFILE = "local";
    private final HttpServletRequest request;

    @Value("${spring.profiles.active:}")
    private String profile;

    public ErrorHandler(final HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handle(Throwable e) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR,e, ErrorCode.INTERNAL_ERROR);
    }
    @ExceptionHandler({NonTargetRestClientException.class, RestClientGenericException.class})
    public ResponseEntity<ErrorResponse> handle(NonTargetRestClientException e) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR,e,e.getCode());
    }
    @ExceptionHandler({NotFoundRestClientException.class, EmptyOrNullBodyRestClientException.class})
    public ResponseEntity<ErrorResponse> handle(GenericException e) {
        log.error(HttpStatus.NOT_FOUND.getReasonPhrase(), e);
        return buildError(HttpStatus.NOT_FOUND,e,e.getCode());
    }
    @ExceptionHandler({BadRequestRestClientException.class})
    public ResponseEntity<ErrorResponse> handle(BadRequestRestClientException e) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), e);
        return buildError(HttpStatus.BAD_REQUEST,e,e.getCode());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex) {
        log.error(ErrorCode.CARD_BAD_REQUEST.getReasons(), ex);
        return buildError(HttpStatus.NOT_FOUND, ex, ErrorCode.CARD_BAD_REQUEST);
    }


@ExceptionHandler({TimeoutRestClientException.class})
    public ResponseEntity<ErrorResponse> handle(TimeoutRestClientException e) {
        log.error(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase(), e);
        return buildError(HttpStatus.REQUEST_TIMEOUT,e,e.getCode());
    }

    private ResponseEntity<ErrorResponse> buildError(HttpStatus httpStatus, Throwable e, ErrorCode code) {
        final var isDebugMessage = DEVELOP_PROFILE.equals(profile) || LOCAL_PROFILE.equals(profile) ? Arrays.toString(e.getStackTrace()) : "";
        final var queryString = Optional.ofNullable(request.getQueryString()).orElse("");
        final var metaData = Map.of(
                "query_string", queryString,
                "stack_trace", isDebugMessage);
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .name(httpStatus.getReasonPhrase())
                        .detail(String.format("%s: %s", e.getClass().getCanonicalName(), e.getMessage()))
                        .status(httpStatus.value())
                        .code(code.getValue())
                        .resource(request.getRequestURI())
                        .metadata(metaData)
                        .build(), httpStatus);

    }
    private ResponseEntity<CustomErrorResponse> customBuildError (HttpStatus httpStatus, Throwable e, ErrorCode code) {
        final var isDebugMessage = DEVELOP_PROFILE.equals(profile) || LOCAL_PROFILE.equals(profile) ? Arrays.toString(e.getStackTrace()) : "";
        final var queryString = Optional.ofNullable(request.getQueryString()).orElse("");
        final var metaData = Map.of(
                "query_string", queryString,
                "stack_trace", isDebugMessage);
        return new ResponseEntity<>(
                CustomErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message(code.getReasons())
                        .status(httpStatus.value())
                        .internalCode(code.getValue())
                        .resource(request.getRequestURI())
                        .build(), httpStatus);

    }
}
