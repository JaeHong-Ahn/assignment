package com.example.thecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@ToString
@NoArgsConstructor
public class UserUpdateForm {

    @NotBlank(message = "변경하실 아이디를 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9]{6,18}$",
            message = "아이디는 숫자, 문자 포함의 6~18자 이내로 작성해주세요.")
    String identifier;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[가-힣]{1,8}$", message = "닉네임은 한글 8자 이내로 입력해주세요.")
    String nickname;

    @NotBlank(message = "이름을 입력해주세요.")
    String name;

    @NotBlank(message = "전화번호를 입력해주세요.")
    String phoneNum;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    String email;
}
