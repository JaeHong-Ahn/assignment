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
    public User create(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return userJpaRepository.findAll(pageable);
    }

    @Override
    public User updateUserInfo(UserUpdateForm userUpdateForm, String identifier) {

        User modifyingUser = userJpaRepository.findUserByIdentifier(identifier);

        User modifiedUser = User.modifyUser(modifyingUser, userUpdateForm);

        return modifiedUser;
    }

    @Override
    public void updateUserPassword(Long id, UserUpdatePasswordForm form) {
        User modifyingUser = userJpaRepository.findById(id).get();
        String encodedPassword = passwordEncoder.encode(form.getPassword());

        User.modifyUserPassword(modifyingUser, encodedPassword);
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
    public Boolean existsByIdentifierToUpdate(String newIdentifier, String originalIdentifier) {
        if (newIdentifier.equals(originalIdentifier)){return false;}

        return userJpaRepository.existsUserByIdentifier(newIdentifier);
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
