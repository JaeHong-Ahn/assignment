package com.example.thecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class UserUpdateIdentifierForm {

    @NotBlank(message = "변경하실 아이디를 입력해주세요.")
    String identifier;

    public UserUpdateIdentifierForm(String identifier) {
        this.identifier = identifier;
    }
}
