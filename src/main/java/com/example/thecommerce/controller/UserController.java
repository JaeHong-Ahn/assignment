package com.example.thecommerce.controller;

import com.example.thecommerce.dto.RegisterRequestDto;
import com.example.thecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String register(@Validated @RequestBody RegisterRequestDto registerRequestDto,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            return null;
        }

        userService.createUser(registerRequestDto);

        return null;

    }
}
