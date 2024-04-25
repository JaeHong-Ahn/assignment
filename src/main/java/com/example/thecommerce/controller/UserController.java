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
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.thecommerce.util.DefaultHttpResponse.*;

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
     * 회원 비밀번호 수정
     * 회원 탈퇴
     */

    //회원 가입
    @PostMapping("/join")
    public ResponseEntity join(@Validated @RequestBody UserRegisterForm form,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        userService.createUser(form);

        return CREATE_SUCCESS_RESPONSE;
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody UserLoginForm form,
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
    public ResponseEntity logout(HttpServletResponse response){
        Cookie cookie = new Cookie("userId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return OK_WITH_NO_DATA;
    }

    //회원 목록 조회
    @GetMapping("/list")
    public ResponseEntity getUserList(@RequestParam(name = "pg", defaultValue = "0") Long page,
                                                        @RequestParam(name = "ps", defaultValue = "10") Long pageSize,
                                                        @RequestParam(name = "option", defaultValue = "LATEST_JOIN") SortOption sortOption){

        Sort sort = getSort(sortOption);

        PageRequest pageRequest = PageRequest.of(page.intValue(), pageSize.intValue(), sort);

        Page<User> usersListPage = userService.findAllUsers(pageRequest);

        return DEFAULT_SUCCESS_RESPONSE(usersListPage);
    }

    //회원 정보 수정
    @PostMapping("/{identifier}")
    public ResponseEntity update(@Validated @RequestBody UserUpdateForm form,
                         @PathVariable String identifier,
                         BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        return DEFAULT_SUCCESS_RESPONSE(userService.updateUserInfo(form, identifier));
    }

    //회원 비밀번호 수정
    @PostMapping("/password/{id}")
    public ResponseEntity updatePassword(@Validated @RequestBody UserUpdatePasswordForm form,
                         @PathVariable Long id,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        userService.updateUserPassword(id, form);

        return OK_WITH_NO_DATA;
    }

    //회원 탈퇴
    @PostMapping("/withdrawal")
    public ResponseEntity withdrawal(HttpServletRequest request){

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
}
