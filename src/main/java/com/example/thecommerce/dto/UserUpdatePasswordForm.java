package com.example.thecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class UserUpdatePasswordForm {

    @NotBlank(message = "변경하실 비밀번호를 입력해주세요.")
    String password;

    @NotBlank(message = "비밀번호 확인을 위해 한번 더 입력해주세요.")
    String checkPassword;

    public UserUpdatePasswordForm(String password, String checkPassword) {
        this.password = password;
        this.checkPassword = checkPassword;
    }
}
