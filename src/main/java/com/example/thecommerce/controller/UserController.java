package com.example.thecommerce.controller;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
     * 회원 아이디
     * 회원 비밀번호 수정
     * 회원 탈퇴
     * 닉네임 중복 확인
     * identifier 중복 확인
     */

    //회원 가입
    @PostMapping("/join")
    public ResponseEntity<? extends Object> join(@Validated @RequestBody UserRegisterForm userRegisterForm,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        userService.createUser(userRegisterForm);

        return CREATE_SUCCESS_RESPONSE;
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<? extends Object> login(@Validated @RequestBody UserLoginForm form,
                        BindingResult bindingResult, HttpServletResponse response) {

        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        User loginUser = userService.login2(form);

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
    public ResponseEntity<? extends Object> getUserList(){

        List<User> allUsers = userService.findAllUsers();
        //추후 쿼리 스트링 방식으로 반환
        return DEFAULT_SUCCESS_RESPONSE(allUsers);
    }

    //회원 정보 수정
    @PostMapping("/{identifier}")
    public ResponseEntity<? extends Object> update(@Validated @RequestBody UserUpdateForm userUpdateForm,
                         @PathVariable String identifier,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        UserUpdateResponseDto dto = userService.updateUserInfo(identifier, userUpdateForm);
        return DEFAULT_SUCCESS_RESPONSE(dto);
    }

    //회원 Identifier 수정
    @PostMapping("/update/identifier/{id}")
    public ResponseEntity<? extends Object> updateIdentifier(@Validated @RequestBody UserUpdateIdentifierForm userUpdateIdentifierForm,
                         @PathVariable Long id, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        UserUpdateIdentifierResponseDto dto = userService.updateUserIdentifier(id, userUpdateIdentifierForm);

        return DEFAULT_SUCCESS_RESPONSE(dto);
    }

    //회원 비밀번호 수정
    @PostMapping("/update/password/{id}")
    public ResponseEntity<? extends Object>  updatePassword(@Validated @RequestBody UserUpdatePasswordForm userUpdatePasswordForm,
                         @PathVariable Long id,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return DEFAULT_BINDING_ERROR_RESPONSE(bindingResult);
        }

        userService.updateUserPassword(id, userUpdatePasswordForm);
        return OK_WITH_NO_DATA;
    }

    //회원 탈퇴
    @PostMapping("/withdrawal")
    public ResponseEntity<? extends Object> withdrawal(HttpServletRequest request){

        Cookie cookie = request.getCookies()[0];

        userService.withdrawal(Long.valueOf(cookie.getValue()));
        return OK_WITH_NO_DATA;
    }
}
