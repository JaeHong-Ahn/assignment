package com.example.thecommerce.repository.interfaces;

public interface UserExistsRepository {

    Boolean existsByIdentifierAndNotDeleted(String identifier);

    Boolean existsByNicknameAndNotDeleted(String nickname);

    Boolean existsByPhoneNumAndNotDeleted(String phoneNum);

    Boolean existsByEmailAndNotDeleted(String email);
}
