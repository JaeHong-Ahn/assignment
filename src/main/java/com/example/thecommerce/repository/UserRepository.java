package com.example.thecommerce.repository;

import com.example.thecommerce.dto.UserRegisterForm;
import com.example.thecommerce.dto.UserUpdateForm;
import com.example.thecommerce.dto.UserUpdateIdentifierForm;
import com.example.thecommerce.dto.UserUpdatePasswordForm;
import com.example.thecommerce.entity.User;

import java.util.List;

public interface UserRepository {

    void create(UserRegisterForm userRegisterForm);

    List<User> findAllUsers();

    void updateUserInfo(String identifier, UserUpdateForm userUpdateForm);

    void updateUserIdentifier(Long id, UserUpdateIdentifierForm form);

    void updateUserPassword(Long id, UserUpdatePasswordForm form);
}
