package com.example.thecommerce.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    DUPLICATED_IDENTIFIER_ERROR(BAD_REQUEST, "중복된 아이디입니다."),
    DUPLICATED_PHONE_NUM_ERROR(BAD_REQUEST, "중복된 전화번호입니다."),
    DUPLICATED_NICKNAME_ERROR(BAD_REQUEST, "중복된 닉네임 입니다."),
    DUPLICATED_EMAIL_ERROR(BAD_REQUEST, "중복된 이메일 입니다."),
    DIFFERENT_PASSWORD_ERROR(BAD_REQUEST, "패스워드가 다릅니다."),

    SORT_NOT_FOUND(BAD_REQUEST, "해당 sort option을 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String message;
}
