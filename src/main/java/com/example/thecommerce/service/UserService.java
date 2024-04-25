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

import javax.servlet.http.HttpServletRequest;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(UserRegisterForm form) {
        validateJoin(form);
        User saved = userRepository.create(UserRegisterForm.toUser(passwordEncoder, form));
        if (saved == null) {
            throw new CustomException(ErrorCode.FAILED_TO_SIGN_UP);
        }
    }

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAllUsers(pageable);
    }

    @Transactional
    public UserUpdateResponseDto updateUserInfo(UserUpdateForm form, String identifier, HttpServletRequest request) {
        User modifyingUser = userRepository.findUserById(Long.valueOf(request.getCookies()[0].getValue()));
        User targetuser = userRepository.findUserByIdentifier(identifier);
        
        if (modifyingUser.equals(targetuser)) {
            validateUpdate(form, identifier);
            return UserUpdateResponseDto.toDto(userRepository.updateUserInfo(form, identifier));
        }
        else {
            throw new CustomException(ErrorCode.WRONG_ACCESS);
        }
    }

    @Transactional
    public void updateUserPassword(Long id, UserUpdatePasswordForm form) {
        validatePasswordUpdateForm(form);
        userRepository.updateUserPassword(id, form);
    }

    @Transactional
    public void withdrawal(Long id) {
        User deletedUser = userRepository.findUserById(id);
        User.toDelete(deletedUser);
    }

    public User login(UserLoginForm form){
        return userRepository.findByLoginId(form.getIdentifier())
                .filter(u -> passwordEncoder.matches(form.getPassword(), u.getPassword()) && u.getIsDeleted().equals(false))
                .orElseThrow(() -> new CustomException(ErrorCode.FAILED_TO_LOGIN));
    }

    public void validateJoin(UserRegisterForm form){
        isDuplicateIdentifier(form.getIdentifier());
        isDuplicateNickname(form.getNickname());
        isDuplicatePhoneNum(form.getPhoneNum());
        isDuplicateEmail(form.getEmail());
    }

    public void validateUpdate(UserUpdateForm form, String identifier){
        User original = userRepository.findUserByIdentifier(identifier);
        isDuplicateIdentifierToUpdate(form.getIdentifier(), original);
        isDuplicateNicknameToUpdate(form.getNickname(), original);
        isDuplicatePhoneNumToUpdate(form.getPhoneNum(), original);
        isDuplicateEmailToUpdate(form.getEmail(), original);
    }

    public void isDuplicateIdentifier(String identifier) {
        Boolean isDuplicated = userRepository.existsByIdentifierAndNotDeleted(identifier);
        if (isDuplicated) {
            log.print("아이디 중복 오류 identifier={" + identifier + "}");
            throw new CustomException(ErrorCode.DUPLICATED_IDENTIFIER_ERROR);
        }
    }

    public void isDuplicateNickname(String nickname) {
        Boolean isDuplicated = userRepository.existsByNicknameAndNotDeleted(nickname);
        if (isDuplicated) {
            log.print("닉네임 중복 오류 nickname={" + nickname + "}");
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME_ERROR);
        }
    }

    public void isDuplicatePhoneNum(String phoneNum) {
        Boolean isDuplicated = userRepository.existsByPhoneNumAndNotDeleted(phoneNum);
        if (isDuplicated) {
            log.print("전화번호 중복 오류 email={" + phoneNum + "}");
            throw new CustomException(ErrorCode.DUPLICATED_PHONE_NUM_ERROR);
        }
    }

    public void isDuplicateEmail(String email) {
        Boolean isDuplicated = userRepository.existsByEmailAndNotDeleted(email);
        if (isDuplicated) {
            log.print("이메일 중복 오류 email={" + email + "}");
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL_ERROR);
        }
    }

    public void isDuplicateIdentifierToUpdate(String identifier, User user) {
        if (userRepository.existsByIdentifierAndNotDeleted(identifier) && !user.getIdentifier().equals(identifier)) {
            log.print("아이디 중복 오류 identifier={" + identifier + "}");
            throw new CustomException(ErrorCode.DUPLICATED_IDENTIFIER_ERROR);
        }
    }

    public void isDuplicateNicknameToUpdate(String nickname, User user) {
        if (userRepository.existsByNicknameAndNotDeleted(nickname) && !user.getNickname().equals(nickname)) {
            log.print("닉네임 중복 오류 nickname={" + nickname + "}");
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME_ERROR);
        }
    }

    public void isDuplicatePhoneNumToUpdate(String phoneNum, User user) {
        if (userRepository.existsByPhoneNumAndNotDeleted(phoneNum) && !user.getPhoneNum().equals(phoneNum)) {
            log.print("전화번호 중복 오류 email={" + phoneNum + "}");
            throw new CustomException(ErrorCode.DUPLICATED_PHONE_NUM_ERROR);
        }
    }

    public void isDuplicateEmailToUpdate(String email, User user) {
        if (userRepository.existsByEmailAndNotDeleted(email) && !user.getEmail().equals(email)) {
            log.print("이메일 중복 오류 email={" + email + "}");
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL_ERROR);
        }
    }

    private void validatePasswordUpdateForm(UserUpdatePasswordForm form) {
        if (!form.getPassword().equals(form.getCheckPassword())) {
            log.print("비밀번호 불일치={"+ form.getPassword() +"},{" + form.getCheckPassword() + "}");
            throw new CustomException(ErrorCode.DIFFERENT_PASSWORD_ERROR);
        }
    }
}
