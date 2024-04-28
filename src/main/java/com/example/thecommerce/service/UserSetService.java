package com.example.thecommerce.service;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.exception.CustomException;
import com.example.thecommerce.exception.ErrorCode;
import com.example.thecommerce.repository.interfaces.UserFindRepository;
import com.example.thecommerce.repository.interfaces.UserSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserSetService {

    private final UserFindRepository userFindRepository;
    private final UserSetRepository userSetRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidations validations;

    //회원 가입
    public User createUser(UserRegisterForm form) {
        validations.validateJoin(form);
        form.setPassword(passwordEncoder.encode(form.getPassword()));
        User saved = userSetRepository.create(UserRegisterForm.toUser(form));
        if (saved == null) {
            throw new CustomException(ErrorCode.FAILED_TO_SIGN_UP);
        }
        return saved;
    }

    //회원 정보 수정
    public UserUpdateResponseDto updateUserInfo(UserUpdateForm form, String identifier, Long id) {
        User modifyingUser = userFindRepository.findUserById(id);
        User targetuser = userFindRepository.findUserByIdentifier(identifier);
        
        if (modifyingUser.equals(targetuser)) {
            validations.validateUpdate(form, identifier);
            return UserUpdateResponseDto.toDto(userSetRepository.updateUserInfo(form, identifier));
        }
        else {
            throw new CustomException(ErrorCode.WRONG_ACCESS);
        }
    }

    //회원 비밀번호 변경
    public void updateUserPassword(Long id, UserUpdatePasswordForm form) {
        validations.validatePasswordUpdateForm(form);
        userSetRepository.updateUserPassword(id, form);
    }

    //회원 탈퇴
    public void withdrawal(Long id) {
        User deletedUser = userFindRepository.findUserById(id);
        User.toDelete(deletedUser);
    }

}
