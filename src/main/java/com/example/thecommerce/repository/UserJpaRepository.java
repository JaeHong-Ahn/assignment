package com.example.thecommerce.repository;

import com.example.thecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    User findUserByIdentifier(String identifier);

    Boolean existsUserByIdentifier(String identifier);

    Boolean existsUserByNickname(String nickname);

    Boolean existsUserByPhoneNum(String phoneNum);

    Boolean existsUserByEmail(String email);
}
