package com.example.thecommerce.repository;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void create(UserRegisterForm userRegisterForm) {
        userJpaRepository.save(UserRegisterForm.toUser(passwordEncoder, userRegisterForm));
        System.out.println(userRegisterForm.getNickname());
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return userJpaRepository.findAll(pageable);
    }

    @Override
    public UserUpdateResponseDto updateUserInfo(String identifier, UserUpdateForm userUpdateForm) {
        User findUser = userJpaRepository.findUserByIdentifier(identifier);

        findUser.setNickname(userUpdateForm.getNickname());
        findUser.setName(userUpdateForm.getName());
        findUser.setPhoneNum(userUpdateForm.getPhoneNum());
        findUser.setEmail(userUpdateForm.getEmail());

        return UserUpdateResponseDto.toDto(findUser);
    }

    @Override
    public UserUpdateIdentifierResponseDto updateUserIdentifier(Long id, UserUpdateIdentifierForm form) {
        User findUser = userJpaRepository.findById(id).get();
        findUser.setIdentifier(form.getIdentifier());
        return UserUpdateIdentifierResponseDto.toDto(findUser);
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
    public Optional<User> findByLoginId(String identifier){
        return userJpaRepository.findAll().stream()
                .filter(m -> m.getIdentifier().equals(identifier))
                .findFirst();
    }

    @Override
    public Boolean existsByIdentifier(String identifier) {
        return userJpaRepository.existsUserByIdentifier(identifier);
    }

    @Override
    public Boolean existsByIdentifierToUpdate(String identifier, Long id) {

        if (userJpaRepository.findById(id).get().getIdentifier().equals(identifier)){
            return false;
        }

        return userJpaRepository.existsUserByIdentifier(identifier);
    }

    @Override
    public Boolean existsByNickname(String nickname) {
        return userJpaRepository.existsUserByNickname(nickname);
    }

    @Override
    public Boolean existsByPhoneNum(String phoneNum) {
        return userJpaRepository.existsUserByPhoneNum(phoneNum);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userJpaRepository.existsUserByEmail(email);
    }
}
