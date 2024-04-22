package com.example.thecommerce.repository;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private PasswordEncoder passwordEncoder;
    private UserJpaRepository userJpaRepository;

    @Override
    public void create(UserRegisterForm userRegisterForm) {
        userJpaRepository.save(UserRegisterForm.toUser(passwordEncoder, userRegisterForm));
    }

    @Override
    public List<User> findAllUsers() {
        return userJpaRepository.findAll();
    }

    @Override
    public void updateUserInfo(String identifier, UserUpdateForm userUpdateForm) {
        User findUser = userJpaRepository.findUserByIdentifier(identifier);
        findUser.setNickname(userUpdateForm.getNickname());
        findUser.setName(userUpdateForm.getName());
        findUser.setPhoneNum(userUpdateForm.getPhoneNum());
        findUser.setEmail(userUpdateForm.getEmail());
    }

    @Override
    public void updateUserIdentifier(Long id, UserUpdateIdentifierForm form) {
        User findUser = userJpaRepository.findById(id).get();
        findUser.setIdentifier(form.getIdentifier());
    }

    @Override
    public void updateUserPassword(Long id, UserUpdatePasswordForm form) {
        User findUser = userJpaRepository.findById(id).get();
        findUser.setPassword(passwordEncoder.encode(form.getPassword()));
    }

    @Override
    public void delete(Long id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public void login(UserLoginForm form) {
        if (userJpaRepository.existsUserByIdentifier(form.getIdentifier())){
            if (passwordEncoder.matches(form.getPassword(), userJpaRepository.findUserByIdentifier(form.getIdentifier()).getPassword())) {
                //success
            }

        }
        //fail
    }
}
