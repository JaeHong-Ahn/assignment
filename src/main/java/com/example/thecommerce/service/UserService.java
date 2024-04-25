package com.example.thecommerce.service;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.exception.CustomException;
import com.example.thecommerce.exception.ErrorCode;
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

    public User login(UserLoginForm form){
        return userRepository.findByLoginId(form.getIdentifier())
                .filter(u -> passwordEncoder.matches(form.getPassword(), u.getPassword()) && u.getIsDeleted().equals(false))
                .orElseThrow(() -> new CustomException(ErrorCode.FAILED_TO_LOGIN));
    }

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAllUsers(pageable);
    }
}
