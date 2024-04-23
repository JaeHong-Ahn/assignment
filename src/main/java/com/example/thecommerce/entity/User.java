package com.example.thecommerce.entity;

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

}
