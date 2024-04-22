package com.example.thecommerce.dto;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserUpdatePasswordForm {

    @NotNull
    String password;

    @NotNull
    String checkPassword;

    public UserUpdatePasswordForm(String password, String checkPassword) {
        this.password = password;
        this.checkPassword = checkPassword;
    }
}
