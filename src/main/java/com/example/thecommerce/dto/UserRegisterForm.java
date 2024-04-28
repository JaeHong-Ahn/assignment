package com.example.thecommerce.dto;

import com.example.thecommerce.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserRegisterForm {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9]{6,18}$",
            message = "아이디는 숫자, 문자 포함의 6~18자 이내로 작성해주세요.")
    String identifier;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=*]).*$",
            message = "비밀번호는 숫자, 문자, 특수문자 포함의 8~15자 이내로 작성해주세요.")
    String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^[가-힣]{1,8}$", message = "닉네임은 한글 8자 이내로 입력해주세요.")
    String nickname;

    @NotBlank(message = "성함을 입력해주세요.")
    String name;

    @NotBlank(message = "전화번호를 입력해주세요.")
    String phoneNum;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
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

    public static User toUser(UserRegisterForm userRegisterForm) {
        return User.builder()
                .identifier(userRegisterForm.getIdentifier())
                .password(userRegisterForm.getPassword())
                .nickname(userRegisterForm.getNickname())
                .name(userRegisterForm.getName())
                .phoneNum(userRegisterForm.getPhoneNum())
                .email(userRegisterForm.getEmail())
                .build();
    }
}
