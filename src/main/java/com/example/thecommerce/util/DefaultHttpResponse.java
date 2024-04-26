package com.example.thecommerce.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

public interface DefaultHttpResponse {

    //앱 상태 확인
    ResponseEntity<SuccessResponseBody> OK_WITH_NO_DATA = ResponseEntity.ok(new SuccessResponseBody(
            String.valueOf(HttpStatus.OK.value()),
            DefaultHttpMessage.OK,
            null));

    //앱 상태 확인
    ResponseEntity<SuccessResponseBody> CREATE_SUCCESS_RESPONSE = ResponseEntity.ok(new SuccessResponseBody(
            String.valueOf(HttpStatus.CREATED.value()),
            DefaultHttpMessage.OK,
            null));
    //그 외
    static ResponseEntity<SuccessResponseBody> DEFAULT_SUCCESS_RESPONSE(Object data) {
        return ResponseEntity.ok(new SuccessResponseBody(
                String.valueOf(HttpStatus.OK.value()),
                DefaultHttpMessage.OK,
                data));
    }

    static ResponseEntity<ExceptionResponseBody> DEFAULT_BINDING_ERROR_RESPONSE(BindingResult bindingResult) {

        List<String> errorList = bindingResult.getFieldErrors()
                .stream().map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(
                new ExceptionResponseBody(
                        String.valueOf(HttpStatus.BAD_REQUEST.value()), errorList, null));
    }

    static ResponseEntity<ExceptionResponseBody> DEFAULT_ERROR_RESPONSE(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(
                new ExceptionResponseBody(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        message,
                        null));
    }

}
