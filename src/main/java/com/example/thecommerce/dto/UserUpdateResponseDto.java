package com.example.thecommerce.dto;

import com.example.thecommerce.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@ToString
@NoArgsConstructor
public class UserUpdateResponseDto {

    @NotNull
    private String nickname;

    @NotNull
    private String name;

    @NotNull
    private String phoneNum;

    @NotNull
    private String email;

    public UserUpdateResponseDto(String nickname, String name,
                                 String phoneNum, String email) {
        this.nickname = nickname;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public static UserUpdateResponseDto toDto(User user){
        return UserUpdateResponseDto.builder()
                .nickname(user.getNickname())
                .name(user.getName())
                .phoneNum(user.getPhoneNum())
                .email(user.getEmail())
                .build();
    }
}
