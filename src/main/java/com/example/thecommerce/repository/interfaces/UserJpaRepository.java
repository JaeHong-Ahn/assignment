package com.example.thecommerce.repository.interfaces;

import com.example.thecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    User findUserByIdentifier(String identifier);

    User findUserById(Long id);

    Boolean existsUserByIdentifier_AndIsDeleted(String identifier, Boolean isDeleted);

    Boolean existsUserByNickname_AndIsDeleted(String nickname, Boolean isDeleted);

    Boolean existsUserByPhoneNum_AndIsDeleted(String phoneNum, Boolean isDeleted);

    Boolean existsUserByEmail_AndIsDeleted(String email, Boolean isDeleted);
}
