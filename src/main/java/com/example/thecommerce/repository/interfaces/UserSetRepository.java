package com.example.thecommerce.repository.interfaces;

import com.example.thecommerce.dto.UserUpdateForm;
import com.example.thecommerce.dto.UserUpdatePasswordForm;
import com.example.thecommerce.entity.User;

public interface UserSetRepository {

    User create(User user);

    User updateUserInfo(UserUpdateForm userUpdateForm, String identifier);

    void updateUserPassword(Long id, UserUpdatePasswordForm form);
}
