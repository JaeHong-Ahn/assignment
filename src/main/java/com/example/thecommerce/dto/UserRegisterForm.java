package com.example.thecommerce.dto;

import com.example.thecommerce.entity.User;
import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@ToString
@NoArgsConstructor
public class UserRegisterForm {

    @NotNull
    String identifier;

    @NotNull
    String password;

    @NotNull
    String nickname;

    @NotNull
    String name;

    @NotNull
    String phoneNum;

    @NotNull
    String email;

    public UserRegisterForm(String identifier, String password,
                            String nickname, String name, String phoneNum, String email) {
        this.identifier = identifier;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public static User toUser(PasswordEncoder passwordEncoder, UserRegisterForm userRegisterForm) {
        return User.builder()
                .identifier(userRegisterForm.getIdentifier())
                .password(passwordEncoder.encode(userRegisterForm.getPassword()))
                .nickname(userRegisterForm.getNickname())
                .name(userRegisterForm.getName())
                .phoneNum(userRegisterForm.getPhoneNum())
                .email(userRegisterForm.getEmail())
                .build();
    }
}
