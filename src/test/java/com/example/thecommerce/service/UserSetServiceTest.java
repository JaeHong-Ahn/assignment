package com.example.thecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.thecommerce.dto.UserUpdatePasswordForm;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.repository.interfaces.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserSetServiceTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserSetService userSetService;

    @Test
    void updateUserPassword() {
        // given
        Long userId = 1L;
        String pw = "rkwhr387@";
        User user = new User(userId, "woghd387", passwordEncoder.encode(pw), "재홍브로",
                "안재홍", "01025865555", "woghd387@naver.com", false);
        when(userJpaRepository.save(user)).thenReturn(user);

        String newPassword = "rkwhr3872@";
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);

        // when
        UserUpdatePasswordForm form = new UserUpdatePasswordForm(newPassword, newPassword);

        userSetService.updateUserPassword(userId, form);

        // then
        System.out.println(encodedNewPassword);
        assertTrue(passwordEncoder.matches(encodedNewPassword, user.getPassword()));
    }

}
