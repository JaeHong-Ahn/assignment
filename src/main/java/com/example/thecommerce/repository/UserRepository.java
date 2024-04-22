package com.example.thecommerce.repository;

import com.example.thecommerce.dto.RegisterRequestDto;

public interface UserRepository {

    void create(RegisterRequestDto registerRequestDto);
}
