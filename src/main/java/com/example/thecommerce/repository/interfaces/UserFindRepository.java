package com.example.thecommerce.repository.interfaces;

import com.example.thecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserFindRepository {

    Page<User> findAllUsers(Pageable pageable);

    Optional<User> findByLoginId(String identifier);

    User findUserById(Long id);

    User findUserByIdentifier(String identifier);
}
