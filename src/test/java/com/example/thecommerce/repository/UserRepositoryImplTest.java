package com.example.thecommerce.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.thecommerce.dto.UserUpdateForm;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.repository.interfaces.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Test
    void create() {
        // given
        String pw = "rkwhr387@";
        User user = new User(1L, "woghd387", passwordEncoder.encode(pw), "재홍브로",
                "안재홍", "01025865555", "woghd387@naver.com", false);
        when(userJpaRepository.save(user)).thenReturn(user);

        // when
        User createdUser = userRepository.create(user);

        // then
        assertEquals(user, createdUser);
    }

    @Test
    void findAllUsers() {
        // given
        Page<User> usersPage = mock(Page.class);
        when(userJpaRepository.findAllByIsDeleted(any(Pageable.class), eq(false))).thenReturn(usersPage);

        // when
        Page<User> result = userRepository.findAllUsers(Pageable.unpaged());

        // then
        assertEquals(usersPage, result);
    }

    @Test
    void updateUserInfo() {
        // given
        String identifier = "woghd387";
        String pw = "rkwhr387@";
        User modifyingUser = new User(1L, identifier, passwordEncoder.encode(pw), "재홍브로",
                "안재홍", "01025865555", "woghd387@naver.com", false);
        UserUpdateForm userUpdateForm = new UserUpdateForm("woghd3872", "재홍브로", "안재홍", "01025865555", "woghd387@naver.com");

        when(userJpaRepository.findUserByIdentifier(identifier)).thenReturn(modifyingUser);

        // when
        User modifiedUser = userRepository.updateUserInfo(userUpdateForm, identifier);

        // then
        assertNotNull(modifiedUser);
        assertEquals("woghd3872", modifiedUser.getIdentifier());
    }

    @Test
    void findByLoginId() {
        // given
        String identifier = "woghd387";
        String pw = "rkwhr387@";
        User user = new User(1L, identifier, passwordEncoder.encode(pw), "재홍브로",
                "안재홍", "01025865555", "woghd387@naver.com", false);
        user.setIdentifier(identifier);
        when(userJpaRepository.findAll()).thenReturn(Collections.singletonList(user));

        // when
        Optional<User> result = userRepository.findByLoginId(identifier);

        // then
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void findUserByIdentifier() {
        // given
        String identifier = "woghd387";
        String pw = "rkwhr387@";
        User user = new User(1L, identifier, passwordEncoder.encode(pw), "재홍브로",
                "안재홍", "01025865555", "woghd387@naver.com", false);

        when(userJpaRepository.findUserByIdentifier(identifier)).thenReturn(user);

        // when
        User result = userRepository.findUserByIdentifier(identifier);

        // then
        assertEquals(user, result);
    }
}
