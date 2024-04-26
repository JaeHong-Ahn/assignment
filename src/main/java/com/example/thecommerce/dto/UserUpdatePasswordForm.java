package com.example.thecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@ToString
@NoArgsConstructor
public class UserUpdatePasswordForm {

    @NotBlank(message = "변경하실 비밀번호를 입력해주세요.")
    @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=*]).*$",
            message = "비밀번호는 숫자, 문자, 특수문자 포함의 8~15자 이내로 작성해주세요.")
    String password;

    @NotBlank(message = "비밀번호 확인을 위해 한번 더 입력해주세요.")
    String checkPassword;

    public UserUpdatePasswordForm(String password, String checkPassword) {
        this.password = password;
        this.checkPassword = checkPassword;
    }
}
