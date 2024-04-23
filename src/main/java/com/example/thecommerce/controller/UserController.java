package com.example.thecommerce.controller;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입
     * 닉네임 중복 확인
     * identifier 중복 확인
     * 회원 목록 조회
     * 회원 정보 수정
     * 회원 아이디
     * 회원 비밀번호 수정
     * 회원 탈퇴
     */

    //회원 가입
    @PostMapping("/join")
    public String join(@Validated @RequestBody UserRegisterForm userRegisterForm,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            System.out.println("바인딩 에러");
            return "bindingError";
        }

        System.out.println(userRegisterForm.getName());
        userService.createUser(userRegisterForm);

        return "Register Success";
    }

    //로그인
    @PostMapping("/login")
    public String login(@Validated @RequestBody UserLoginForm form,
                        BindingResult bindingResult, HttpServletResponse response,
                        HttpSession session) {

        if (bindingResult.hasErrors()){
            return null;
        }

        User loginUser = userService.login2(form);

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return null;
        }

        Cookie idCookie = new Cookie("userId", String.valueOf(loginUser.getId()));
        //세션 쿠키 사용
        response.addCookie(idCookie);

        return "Login Success";
    }

    //로그 아웃
    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie cookie = new Cookie("userId", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "Logout Success";
    }

    //회원 목록 조회
    @GetMapping("/list")
    public List<User> getUserList(){
        return userService.findAllUsers();
    }

    //회원 정보 수정
    @PostMapping("/{identifier}")
    public String update(@Validated @RequestBody UserUpdateForm userUpdateForm,
                         @PathVariable String identifier,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return null;
        }

        userService.updateUserInfo(identifier, userUpdateForm);
        return "UserInfo Update Success";
    }

    //회원 Identifier 수정
    @PostMapping("/update/identifier/{id}")
    public String updateIdentifier(@Validated @RequestBody UserUpdateIdentifierForm userUpdateIdentifierForm,
                         @PathVariable Long id,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return null;
        }

        userService.updateUserIdentifier(id, userUpdateIdentifierForm);
        return "Identifier Update Success";
    }

    //회원 비밀번호 수정
    @PostMapping("/update/password/{id}")
    public String updatePassword(@Validated @RequestBody UserUpdatePasswordForm userUpdatePasswordForm,
                         @PathVariable Long id,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return null;
        }

        userService.updateUserPassword(id, userUpdatePasswordForm);
        return "Password Update Success";
    }

    //회원 탈퇴
    @PostMapping("/withdrawal")
    public String withdrawal(HttpServletRequest request){

        Cookie cookie = request.getCookies()[0];

        userService.withdrawal(Long.valueOf(cookie.getValue()));
        return "withdrawal success";
    }
}
