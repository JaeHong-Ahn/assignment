package com.example.thecommerce.dto;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserUpdateIdentifierForm {

    @NotNull
    String identifier;

    public UserUpdateIdentifierForm(String identifier) {
        this.identifier = identifier;
    }
}
