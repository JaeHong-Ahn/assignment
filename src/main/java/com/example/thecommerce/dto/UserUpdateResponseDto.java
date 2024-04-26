package com.example.thecommerce.dto;

import com.example.thecommerce.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@ToString
public class UserUpdateResponseDto {

    @NotNull
    private String identifier;

    @NotNull
    private String nickname;

    @NotNull
    private String name;

    @NotNull
    private String phoneNum;

    @NotNull
    private String email;

    public static UserUpdateResponseDto toDto(User user){
        return UserUpdateResponseDto.builder()
                .identifier(user.getIdentifier())
                .nickname(user.getNickname())
                .name(user.getName())
                .phoneNum(user.getPhoneNum())
                .email(user.getEmail())
                .build();
    }
}
