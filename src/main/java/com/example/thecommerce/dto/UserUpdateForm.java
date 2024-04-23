package com.example.thecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class UserUpdateForm {

    @NotBlank(message = "닉네임을 입력해주세요.")
    String nickname;

    @NotBlank(message = "이름을 입력해주세요.")
    String name;

    @NotBlank(message = "전화번호를 입력해주세요.")
    String phoneNum;

    @NotBlank(message = "이메일을 입력해주세요.")
    String email;

    public UserUpdateForm(String nickname, String name,
                          String phoneNum, String email) {
        this.nickname = nickname;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
    }
}
