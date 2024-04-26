package com.example.thecommerce.service;

import com.example.thecommerce.dto.UserRegisterForm;
import com.example.thecommerce.dto.UserUpdateForm;
import com.example.thecommerce.dto.UserUpdatePasswordForm;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.exception.CustomException;
import com.example.thecommerce.exception.ErrorCode;
import com.example.thecommerce.repository.interfaces.UserExistsRepository;
import com.example.thecommerce.repository.interfaces.UserFindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserValidations {

    private final UserFindRepository userFindRepository;
    private final UserExistsRepository userExistsRepository;

    public void validateJoin(UserRegisterForm form){
        isDuplicateIdentifier(form.getIdentifier());
        isDuplicateNickname(form.getNickname());
        isDuplicatePhoneNum(form.getPhoneNum());
        isDuplicateEmail(form.getEmail());
    }

    public void validateUpdate(UserUpdateForm form, String identifier){
        User original = userFindRepository.findUserByIdentifier(identifier);
        isDuplicateIdentifierToUpdate(form.getIdentifier(), original);
        isDuplicateNicknameToUpdate(form.getNickname(), original);
        isDuplicatePhoneNumToUpdate(form.getPhoneNum(), original);
        isDuplicateEmailToUpdate(form.getEmail(), original);
    }

    public void isDuplicateIdentifier(String identifier) {
        Boolean isDuplicated = userExistsRepository.existsByIdentifierAndNotDeleted(identifier);
        if (isDuplicated) {
            log.print("아이디 중복 오류 identifier={" + identifier + "}");
            throw new CustomException(ErrorCode.DUPLICATED_IDENTIFIER_ERROR);
        }
    }

    public void isDuplicateNickname(String nickname) {
        Boolean isDuplicated = userExistsRepository.existsByNicknameAndNotDeleted(nickname);
        if (isDuplicated) {
            log.print("닉네임 중복 오류 nickname={" + nickname + "}");
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME_ERROR);
        }
    }

    public void isDuplicatePhoneNum(String phoneNum) {
        Boolean isDuplicated = userExistsRepository.existsByPhoneNumAndNotDeleted(phoneNum);
        if (isDuplicated) {
            log.print("전화번호 중복 오류 email={" + phoneNum + "}");
            throw new CustomException(ErrorCode.DUPLICATED_PHONE_NUM_ERROR);
        }
    }

    public void isDuplicateEmail(String email) {
        Boolean isDuplicated = userExistsRepository.existsByEmailAndNotDeleted(email);
        if (isDuplicated) {
            log.print("이메일 중복 오류 email={" + email + "}");
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL_ERROR);
        }
    }

    public void isDuplicateIdentifierToUpdate(String identifier, User user) {
        if (userExistsRepository.existsByIdentifierAndNotDeleted(identifier) && !user.getIdentifier().equals(identifier)) {
            log.print("아이디 중복 오류 identifier={" + identifier + "}");
            throw new CustomException(ErrorCode.DUPLICATED_IDENTIFIER_ERROR);
        }
    }

    public void isDuplicateNicknameToUpdate(String nickname, User user) {
        if (userExistsRepository.existsByNicknameAndNotDeleted(nickname) && !user.getNickname().equals(nickname)) {
            log.print("닉네임 중복 오류 nickname={" + nickname + "}");
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME_ERROR);
        }
    }

    public void isDuplicatePhoneNumToUpdate(String phoneNum, User user) {
        if (userExistsRepository.existsByPhoneNumAndNotDeleted(phoneNum) && !user.getPhoneNum().equals(phoneNum)) {
            log.print("전화번호 중복 오류 email={" + phoneNum + "}");
            throw new CustomException(ErrorCode.DUPLICATED_PHONE_NUM_ERROR);
        }
    }

    public void isDuplicateEmailToUpdate(String email, User user) {
        if (userExistsRepository.existsByEmailAndNotDeleted(email) && !user.getEmail().equals(email)) {
            log.print("이메일 중복 오류 email={" + email + "}");
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL_ERROR);
        }
    }

    public void validatePasswordUpdateForm(UserUpdatePasswordForm form) {
        if (!form.getPassword().equals(form.getCheckPassword())) {
            log.print("비밀번호 불일치={"+ form.getPassword() +"},{" + form.getCheckPassword() + "}");
            throw new CustomException(ErrorCode.DIFFERENT_PASSWORD_ERROR);
        }
    }
}
