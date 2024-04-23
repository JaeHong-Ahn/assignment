package com.example.thecommerce.service;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAllUsers(pageable);
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

    public User login(UserLoginForm form){
        return userRepository.findByLoginId(form.getIdentifier())
                .filter(u -> passwordEncoder.matches(form.getPassword(), u.getPassword()))
                .orElse(null);
    }

    public Boolean isDuplicateIdentifier(String identifier) {
        return userRepository.existsByIdentifier(identifier);
    }

    public Boolean isDuplicateIdentifierToUpdate(String identifier, Long id) {
        return userRepository.existsByIdentifierToUpdate(identifier, id);
    }

    public Boolean isDuplicateNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public Boolean isDuplicatePhoneNum(String phoneNum) {
        return userRepository.existsByPhoneNum(phoneNum);
    }

    public Boolean isDuplicateEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
