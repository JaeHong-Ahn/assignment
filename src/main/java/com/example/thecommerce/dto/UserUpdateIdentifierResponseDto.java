package com.example.thecommerce.dto;

import com.example.thecommerce.entity.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
public class UserUpdateIdentifierResponseDto {

    @NotNull
    private String identifier;

    public UserUpdateIdentifierResponseDto(String identifier) {
        this.identifier = identifier;
    }

    public static UserUpdateIdentifierResponseDto toDto(User user){
        UserUpdateIdentifierResponseDto dto = new UserUpdateIdentifierResponseDto();
        dto.setIdentifier(user.getIdentifier());
        return dto;
    }
}
