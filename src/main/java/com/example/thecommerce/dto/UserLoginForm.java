package com.example.thecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class UserLoginForm {

    @NotBlank(message = "아이디를 입력해주세요.")
    String identifier;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password;

    public UserLoginForm(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }
}
