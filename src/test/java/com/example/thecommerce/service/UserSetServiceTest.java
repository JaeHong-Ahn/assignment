package com.example.thecommerce.service;

import com.example.thecommerce.dto.UserRegisterForm;
import com.example.thecommerce.dto.UserUpdateForm;
import com.example.thecommerce.dto.UserUpdatePasswordForm;
import com.example.thecommerce.dto.UserUpdateResponseDto;
import com.example.thecommerce.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserSetServiceTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSetService userSetService;

    UserRegisterForm userRegisterForm1 = new UserRegisterForm("woghd387","rkwhr387@","재홍브로", "안재홍", "01025865555", "woghd387@naver.com");

    UserUpdateForm userUpdateForm = new UserUpdateForm("woghd3872", "재홍맨", "안재홍", "01025555555", "woghd387@thecommerce.com");

    UserUpdatePasswordForm userUpdatePasswordForm = new UserUpdatePasswordForm("rkwhr387@", "rkwhr387@");

    @Test
    @DisplayName("회원가입")
    void createUser() {
        // given & when
        User saved = userSetService.createUser(userRegisterForm1);

        // then
        assertTrue(saved.getIdentifier().equals(userRegisterForm1.getIdentifier()));
    }

    @Test
    @DisplayName("회원정보변경")
    void updateUserInfo() {
        // given
        User saved = userSetService.createUser(userRegisterForm1);

        //when
        UserUpdateResponseDto dto = userSetService.updateUserInfo(userUpdateForm, saved.getIdentifier(), saved.getId());

        // then
        assertEquals("woghd3872", dto.getIdentifier());
        assertEquals("재홍맨", dto.getNickname());
        assertEquals("안재홍", dto.getName());
        assertEquals("01025555555", dto.getPhoneNum());
        assertEquals("woghd387@thecommerce.com", dto.getEmail());

    }

    @Test
    @DisplayName("비밀번호변경")
    void updateUserPassword() {
        // given
        User saved = userSetService.createUser(userRegisterForm1);

        // when
        userSetService.updateUserPassword(saved.getId(), userUpdatePasswordForm);

        // then
        assertTrue(passwordEncoder.matches("rkwhr387@", saved.getPassword()));
    }

    @Test
    @DisplayName("회원탈퇴")
    void withdrawal() {
        // given
        User saved = userSetService.createUser(userRegisterForm1);

        // when
        userSetService.withdrawal(saved.getId());

        // then
        assertTrue(saved.getIsDeleted());
    }
}
