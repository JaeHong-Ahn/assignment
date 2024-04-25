package com.example.thecommerce.repository;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    User create(User user);

    Page<User> findAllUsers(Pageable pageable);

    User updateUserInfo(UserUpdateForm userUpdateForm, String identifier);

    void updateUserPassword(Long id, UserUpdatePasswordForm form);

    Optional<User> findByLoginId(String identifier);

    User findUserById(Long id);

    Boolean existsByIdentifier(String identifier);

    Boolean existsByIdentifierToUpdate(String newIdentifier, String originalIdentifier);

    Boolean existsByNickname(String nickname);

    Boolean existsByPhoneNum(String phoneNum);

    Boolean existsByEmail(String email);

    User findUserByIdentifier(String identifier);
}
