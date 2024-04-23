package com.example.thecommerce.service;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserRegisterForm form) {
        userRepository.create(form);
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    @Transactional
    public UserUpdateResponseDto updateUserInfo(String identifier, UserUpdateForm form) {
        return userRepository.updateUserInfo(identifier, form);
    }

    @Transactional
    public UserUpdateIdentifierResponseDto updateUserIdentifier(Long id, UserUpdateIdentifierForm form) {
        return userRepository.updateUserIdentifier(id, form);
    }

    @Transactional
    public void updateUserPassword(Long id, UserUpdatePasswordForm form) {
        userRepository.updateUserPassword(id, form);
    }

    @Transactional
    public void withdrawal(Long id) {
        userRepository.delete(id);
    }

    public void login(UserLoginForm form) {
        userRepository.login(form);
    }

    public User login2(UserLoginForm form){
        return userRepository.findByLoginId(form.getIdentifier())
                .filter(u -> passwordEncoder.matches(form.getPassword(), u.getPassword()))
                .orElse(null);
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }
}
