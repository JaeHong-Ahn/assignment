package com.example.thecommerce.service;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.exception.CustomException;
import com.example.thecommerce.exception.ErrorCode;
import com.example.thecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
@Transactional
public class UserSetService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidations validations;

    //회원 가입
    public void createUser(UserRegisterForm form) {
        validations.validateJoin(form);
        User saved = userRepository.create(UserRegisterForm.toUser(passwordEncoder, form));
        if (saved == null) {
            throw new CustomException(ErrorCode.FAILED_TO_SIGN_UP);
        }
    }

    //회원 정보 수정
    public UserUpdateResponseDto updateUserInfo(UserUpdateForm form, String identifier, HttpServletRequest request) {
        User modifyingUser = userRepository.findUserById(Long.valueOf(request.getCookies()[0].getValue()));
        User targetuser = userRepository.findUserByIdentifier(identifier);
        
        if (modifyingUser.equals(targetuser)) {
            validations.validateUpdate(form, identifier);
            return UserUpdateResponseDto.toDto(userRepository.updateUserInfo(form, identifier));
        }
        else {
            throw new CustomException(ErrorCode.WRONG_ACCESS);
        }
    }

    //회원 비밀번호 변경
    public void updateUserPassword(Long id, UserUpdatePasswordForm form) {
        validations.validatePasswordUpdateForm(form);
        userRepository.updateUserPassword(id, form);
    }

    //회원 탈퇴
    public void withdrawal(Long id) {
        User deletedUser = userRepository.findUserById(id);
        User.toDelete(deletedUser);
    }

}
