package com.example.thecommerce.service;

import com.example.thecommerce.dto.UserRegisterForm;
import com.example.thecommerce.dto.UserUpdateForm;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserRegisterForm userRegisterForm) {
        userRepository.create(userRegisterForm);
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    @Transactional
    public void updateUserInfo(String identifier, UserUpdateForm userUpdateForm) {
        userRepository.updateUserInfo(identifier, userUpdateForm);
    }
}
