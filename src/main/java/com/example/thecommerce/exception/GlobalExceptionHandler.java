package com.example.thecommerce.exception;

import com.example.thecommerce.util.ExceptionResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ExceptionResponseBody> handleCustomException(CustomException e) {
        log.error("handleCustomException throw Exception : {}", e.getErrorCode().getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(
                new ExceptionResponseBody(
                        String.valueOf(e.getErrorCode().getStatus().value()), e.getErrorCode().getMessage(), null));
    }

    @ExceptionHandler(value = {HttpClientErrorException.BadRequest.class})
    protected ResponseEntity<ExceptionResponseBody> handleBadRequestException(HttpClientErrorException.BadRequest e) {
        log.error("handleBadRequestException throw Exception : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseBody(
                String.valueOf(e.getStatusCode().value()), "잘못된 요청입니다.", null));
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ExceptionResponseBody> handleException(Exception e) {
        log.error("handleCustomException throw Exception : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ExceptionResponseBody(
                        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                        "서버 내부 오류입니다.",
                        null));
    }
}