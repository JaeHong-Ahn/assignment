package com.example.thecommerce.repository;

import com.example.thecommerce.dto.RegisterRequestDto;
import com.example.thecommerce.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private UserJpaRepository userJpaRepository;

    @Override
    public void create(RegisterRequestDto registerRequestDto) {
        userJpaRepository.save(User.toUser(registerRequestDto));
    }
}
