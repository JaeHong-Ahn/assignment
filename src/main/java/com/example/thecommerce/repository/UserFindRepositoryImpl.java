package com.example.thecommerce.repository;

import com.example.thecommerce.dto.*;
import com.example.thecommerce.entity.User;
import com.example.thecommerce.repository.interfaces.UserExistsRepository;
import com.example.thecommerce.repository.interfaces.UserJpaRepository;
import com.example.thecommerce.repository.interfaces.UserFindRepository;
import com.example.thecommerce.repository.interfaces.UserSetRepository;
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
public class UserFindRepositoryImpl implements UserFindRepository, UserSetRepository, UserExistsRepository {

    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userJpaRepository;

    @Override
    public User create(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return userJpaRepository.findAllByIsDeleted(pageable, false);
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
    public Optional<User> findByLoginId(String identifier){
        return userJpaRepository.findAll().stream()
                .filter(m -> m.getIdentifier().equals(identifier))
                .findFirst();
    }

    @Override
    public User findUserById(Long id) {
        return userJpaRepository.findUserById(id);
    }

    @Override
    public Boolean existsByIdentifierAndNotDeleted(String identifier) {
        return userJpaRepository.existsUserByIdentifier_AndIsDeleted(identifier, false);
    }

    @Override
    public Boolean existsByNicknameAndNotDeleted(String nickname) {
        return userJpaRepository.existsUserByNickname_AndIsDeleted(nickname, false);
    }

    @Override
    public Boolean existsByPhoneNumAndNotDeleted(String phoneNum) {
        return userJpaRepository.existsUserByPhoneNum_AndIsDeleted(phoneNum, false);
    }

    @Override
    public Boolean existsByEmailAndNotDeleted(String email) {
        return userJpaRepository.existsUserByEmail_AndIsDeleted(email, false);
    }

    @Override
    public User findUserByIdentifier(String identifier) {
        return userJpaRepository.findUserByIdentifier(identifier);
    }
}
