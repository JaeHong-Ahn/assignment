package com.example.thecommerce.dto;

import com.example.thecommerce.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class UserRegisterForm {

    @NotBlank(message = "아이디를 입력해주세요.")
    String identifier;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    String nickname;

    @NotBlank(message = "성함을 입력해주세요.")
    String name;

    @NotBlank(message = "전화번호를 입력해주세요.")
    String phoneNum;

    @NotBlank(message = "이메일을 입력해주세요.")
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
