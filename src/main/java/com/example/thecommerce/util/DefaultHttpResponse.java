package com.example.thecommerce.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

}
