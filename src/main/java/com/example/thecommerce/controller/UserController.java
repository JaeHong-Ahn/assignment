package com.example.thecommerce.controller;

import com.example.thecommerce.dto.UserRegisterForm;
import com.example.thecommerce.dto.UserUpdateForm;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * 회원 비밀번호 수정
     * 회원 탈퇴
     */

    //회원 가입
    @PostMapping("/join")
    public String register(@Validated @RequestBody UserRegisterForm userRegisterForm,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return null;
        }

        userService.createUser(userRegisterForm);

        return null;
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

        userService.updateUserInfo(identifier, userUpdateForm);
        return null;
    }

    //회원 Identifier 수정

    //회원 비밀번호 수정
}
