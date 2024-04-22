package com.example.thecommerce.dto;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserUpdateForm {

    @NotNull
    String nickname;

    @NotNull
    String name;

    @NotNull
    String phoneNum;

    @NotNull
    String email;

    public UserUpdateForm(String nickname, String name,
                          String phoneNum, String email) {
        this.nickname = nickname;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
    }
}
