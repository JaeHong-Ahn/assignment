package com.example.thecommerce.repository;

import com.example.thecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

}
