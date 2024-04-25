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

    Boolean existsByIdentifierAndNotDeleted(String identifier);

    Boolean existsByNicknameAndNotDeleted(String nickname);

    Boolean existsByPhoneNumAndNotDeleted(String phoneNum);

    Boolean existsByEmailAndNotDeleted(String email);

    User findUserByIdentifier(String identifier);
}
