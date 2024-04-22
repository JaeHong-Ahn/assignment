package com.example.thecommerce.dto;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserUpdateIdentifierForm {

    @NotNull
    String identifier;

    public UserUpdateIdentifierForm(String identifier) {
        this.identifier = identifier;
    }
}
