package com.example.thecommerce.repository;

import com.example.thecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    User findUserByIdentifier(String identifier);

    Boolean existsUserByIdentifier(String identifier);

    void deleteUserById(Long id);
}
