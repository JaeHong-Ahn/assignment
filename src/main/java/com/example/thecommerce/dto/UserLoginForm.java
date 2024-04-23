package com.example.thecommerce.dto;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserLoginForm {

    @NotNull
    String identifier;

    @NotNull
    String password;

    public UserLoginForm(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }
}
