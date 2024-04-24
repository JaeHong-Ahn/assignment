package com.example.thecommerce.repository;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    void create(UserRegisterForm userRegisterForm);

    Page<User> findAllUsers(Pageable pageable);

    UserUpdateResponseDto updateUserInfo(String identifier, UserUpdateForm userUpdateForm);

    UserUpdateIdentifierResponseDto updateUserIdentifier(Long id, UserUpdateIdentifierForm form);

    void updateUserPassword(Long id, UserUpdatePasswordForm form);

    void delete(Long id);

    Optional<User> findByLoginId(String identifier);

    Boolean existsByIdentifier(String identifier);

    Boolean existsByIdentifierToUpdate(String newIdentifier, String originalIdentifier);

    Boolean existsByNickname(String nickname);

    Boolean existsByPhoneNum(String phoneNum);

    Boolean existsByEmail(String email);
}
