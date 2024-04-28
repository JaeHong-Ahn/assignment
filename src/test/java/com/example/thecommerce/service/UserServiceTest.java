package com.example.thecommerce.service;

import com.example.thecommerce.dto.UserRegisterForm;
import com.example.thecommerce.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSetService userSetService;

    UserRegisterForm userRegisterForm1 = new UserRegisterForm("woghd3871","rkwhr3871@","재홍브로1", "안재홍", "01025865551", "woghd3871@naver.com");
    UserRegisterForm userRegisterForm2 = new UserRegisterForm("woghd3872","rkwhr3872@","재홍브로2", "안재홍", "01025865552", "woghd3872@naver.com");
    UserRegisterForm userRegisterForm3 = new UserRegisterForm("woghd3873","rkwhr3873@","재홍브로3", "안재홍", "01025865553", "woghd3873@naver.com");
    UserRegisterForm userRegisterForm4 = new UserRegisterForm("woghd3874","rkwhr3874@","재홍브로4", "안재홍", "01025865554", "woghd3874@naver.com");


    @Test
    @DisplayName("회원 목록 조회")
    void findAllUsers() {
        //given
        PageRequest pageable = PageRequest.of(0, 2);

        userSetService.createUser(userRegisterForm1);
        userSetService.createUser(userRegisterForm2);
        userSetService.createUser(userRegisterForm3);
        userSetService.createUser(userRegisterForm4);

        //when
        Page<User> allUsers = userService.findAllUsers(pageable);

        //then
        assertTrue(allUsers.getTotalPages() == 2);
        assertTrue(allUsers.getSize() == 2);

    }
}