package com.example.thecommerce.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@ToString
@NoArgsConstructor
public class UserUpdateIdentifierForm {

    @NotBlank(message = "변경하실 아이디를 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9]{6,18}$",
            message = "아이디는 숫자, 문자 포함의 6~18자 이내로 작성해주세요.")
    String identifier;

    public UserUpdateIdentifierForm(String identifier) {
        this.identifier = identifier;
    }
}
