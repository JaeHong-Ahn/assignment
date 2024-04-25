package com.example.thecommerce.entity;

import com.example.thecommerce.dto.UserUpdateForm;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "user")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@Component
@DynamicUpdate
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
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

    public static User modifyUser(User user, UserUpdateForm form){
        user.setIdentifier(form.getIdentifier());
        user.setNickname(form.getNickname());
        user.setName(form.getName());
        user.setPhoneNum(form.getPhoneNum());
        user.setEmail(form.getEmail());
        return user;
    }

    public static void modifyUserPassword(User user, String password){
        user.setPassword(password);
    }

}
