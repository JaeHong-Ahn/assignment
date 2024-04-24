package com.example.thecommerce.controller;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.SortOption;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.exception.CustomException;
import com.example.thecommerce.exception.ErrorCode;
import com.example.thecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.thecommerce.util.DefaultHttpResponse.*;
import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입
     * 로그인
     * 로그아웃
     * 회원 목록 조회
     * 회원 정보 수정
     * 회원 아이디
     * 회원 비밀번호 수정
     * 회원 탈퇴
     * 닉네임 중복 확인
     * identifier 중복 확인
     */

    //회원 가입
    @PostMapping("/join")
    public ResponseEntity<? extends Object> join(@Validated @RequestBody UserRegisterForm form,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        validateRegisterForm(form);

        userService.createUser(form);

        return CREATE_SUCCESS_RESPONSE;
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<? extends Object> login(@Validated @RequestBody UserLoginForm form,
                        BindingResult bindingResult, HttpServletResponse response) {

        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        User loginUser = userService.login(form);

        if (loginUser == null) {
            return DEFAULT_ERROR_RESPONSE("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        Cookie idCookie = new Cookie("userId", String.valueOf(loginUser.getId()));
        //세션 쿠키 사용
        response.addCookie(idCookie);

        return OK_WITH_NO_DATA;
    }

    //로그 아웃
    @PostMapping("/logout")
    public ResponseEntity<? extends Object> logout(HttpServletResponse response){
        Cookie cookie = new Cookie("userId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return OK_WITH_NO_DATA;
    }

    //회원 목록 조회
    @GetMapping("/list")
    public ResponseEntity<? extends Object> getUserList(@RequestParam(name = "pg", defaultValue = "0") Long page,
                                                        @RequestParam(name = "ps", defaultValue = "10") Long pageSize,
                                                        @RequestParam(name = "option", defaultValue = "LATEST_JOIN") SortOption sortOption){

        Sort sort = getSort(sortOption);

        PageRequest pageRequest = PageRequest.of(page.intValue(), pageSize.intValue(), sort);

        Page<User> usersListPage = userService.findAllUsers(pageRequest);

        return DEFAULT_SUCCESS_RESPONSE(usersListPage);
    }

    //회원 정보 수정
    @PostMapping("/{identifier}")
    public ResponseEntity<? extends Object> update(@Validated @RequestBody UserUpdateForm form,
                         @PathVariable String identifier,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        validateUpdateForm(form);

        UserUpdateResponseDto dto = userService.updateUserInfo(identifier, form);
        return DEFAULT_SUCCESS_RESPONSE(dto);
    }

    //회원 Identifier 수정
    @PostMapping("/identifier/{id}")
    public ResponseEntity<? extends Object> updateIdentifier(@Validated @RequestBody UserUpdateIdentifierForm form,
                         @PathVariable Long id, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        validateIdentifierUpdateForm(form, id);

        UserUpdateIdentifierResponseDto dto = userService.updateUserIdentifier(id, form);

        return DEFAULT_SUCCESS_RESPONSE(dto);
    }

    //회원 비밀번호 수정
    @PostMapping("/password/{id}")
    public ResponseEntity<? extends Object>  updatePassword(@Validated @RequestBody UserUpdatePasswordForm form,
                         @PathVariable Long id,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        validatePasswordUpdateForm(form);

        userService.updateUserPassword(id, form);
        return OK_WITH_NO_DATA;
    }

    //회원 탈퇴
    @PostMapping("/withdrawal")
    public ResponseEntity<? extends Object> withdrawal(HttpServletRequest request){

        Cookie cookie = request.getCookies()[0];

        userService.withdrawal(Long.valueOf(cookie.getValue()));
        return OK_WITH_NO_DATA;
    }

    private static Sort getSort(SortOption sortOption) {
        Sort sort;

        switch (sortOption) {
            case EARLY_JOIN:
                sort = Sort.by(Sort.Direction.ASC, "createdAt");
                break;
            case LATEST_JOIN:
                sort = Sort.by(Sort.Direction.DESC, "createdAt");
                break;
            case NAME_DESC:
                sort = Sort.by(Sort.Direction.DESC, "name");
                break;
            case NAME_ASC:
                sort = Sort.by(Sort.Direction.ASC, "name");
                break;
            default:
                throw new CustomException(ErrorCode.SORT_NOT_FOUND);
        }
        return sort;
    }

    private void validateRegisterForm(UserRegisterForm form) {

        if (userService.isDuplicateIdentifier(form.getIdentifier())) {
            log.print("아이디 중복 오류 identifier={" + form.getIdentifier() + "}");
            throw new CustomException(ErrorCode.DUPLICATED_IDENTIFIER_ERROR);
        }
        if (userService.isDuplicateNickname(form.getNickname())) {
            log.print("닉네임 중복 오류 nickname={" + form.getNickname() + "}");
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME_ERROR);
        }
        if (userService.isDuplicateEmail(form.getEmail())) {
            log.print("이메일 중복 오류 email={" + form.getEmail() + "}");
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL_ERROR);
        }
        if (userService.isDuplicatePhoneNum(form.getPhoneNum())) {
            log.print("전화번호 중복 오류 email={" + form.getPhoneNum() + "}");
            throw new CustomException(ErrorCode.DUPLICATED_PHONE_NUM_ERROR);
        }
    }

    private void validateUpdateForm(UserUpdateForm form) {

        if (userService.isDuplicateNickname(form.getNickname())) {
            log.print("닉네임 중복 오류 nickname={" + form.getNickname() + "}");
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME_ERROR);
        }
        if (userService.isDuplicateEmail(form.getEmail())) {
            log.print("이메일 중복 오류 email={" + form.getEmail() + "}");
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL_ERROR);
        }
        if (userService.isDuplicatePhoneNum(form.getPhoneNum())) {
            log.print("전화번호 중복 오류 email={" + form.getPhoneNum() + "}");
            throw new CustomException(ErrorCode.DUPLICATED_PHONE_NUM_ERROR);
        }
    }

    private void validateIdentifierUpdateForm(UserUpdateIdentifierForm form, Long id) {

        if (userService.isDuplicateIdentifierToUpdate(form.getIdentifier(), id)) {
            log.print("아이디 중복 오류 identifier={" + form.getIdentifier() + "}");
            throw new CustomException(ErrorCode.DUPLICATED_IDENTIFIER_ERROR);
        }
    }

    private void validatePasswordUpdateForm(UserUpdatePasswordForm form) {

        if (!form.getPassword().equals(form.getCheckPassword())) {
            log.print("비밀번호 불일치={"+ form.getPassword() +"},{" + form.getCheckPassword() + "}");
            throw new CustomException(ErrorCode.DIFFERENT_PASSWORD_ERROR);
        }
    }
}
