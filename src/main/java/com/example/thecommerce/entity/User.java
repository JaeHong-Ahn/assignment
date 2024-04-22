package com.example.thecommerce.entity;

import com.example.thecommerce.dto.RegisterRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "user")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Component
@DynamicUpdate
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "identifier", unique = true, nullable = false)
    private String identifier;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phoneNum", nullable = false)
    private String phoneNum;

    @Column(name = "email", nullable = false)
    private String email;

    //password -> BcryptEncoder
    public static User toUser(RegisterRequestDto registerRequestDto) {
        return User.builder()
                .identifier(registerRequestDto.getIdentifier())
                .password(registerRequestDto.getPassword())
                .nickname(registerRequestDto.getNickname())
                .name(registerRequestDto.getName())
                .phoneNum(registerRequestDto.getPhoneNum())
                .email(registerRequestDto.getEmail())
                .build();
    }
}
