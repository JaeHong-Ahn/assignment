package com.example.thecommerce.repository;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void create(UserRegisterForm userRegisterForm);

    List<User> findAllUsers();

    void updateUserInfo(String identifier, UserUpdateForm userUpdateForm);

    void updateUserIdentifier(Long id, UserUpdateIdentifierForm form);

    void updateUserPassword(Long id, UserUpdatePasswordForm form);

    void delete(Long id);

    void login(UserLoginForm form);

    Optional<User> findByLoginId(String identifier);

    User findUserById(Long id);

    User findByIdentifier(String identifier);
}
