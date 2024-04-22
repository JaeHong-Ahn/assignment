package com.example.thecommerce.dto;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RegisterRequestDto {

    @NotNull
    String identifier;

    @NotNull
    String password;

    @NotNull
    String nickname;

    @NotNull
    String name;

    @NotNull
    String phoneNum;

    @NotNull
    String email;

    public RegisterRequestDto(String identifier, String password,
                              String nickname, String name, String phoneNum, String email) {
        this.identifier = identifier;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
    }
}
