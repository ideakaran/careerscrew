package com.project.careerscrew.services;

import com.project.careerscrew.dto.GenericResponseDTO;
import com.project.careerscrew.dto.auth.ForgotPasswordRequestDTO;
import com.project.careerscrew.dto.auth.PasswordResetVerRequestDTO;
import com.project.careerscrew.entities.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity findUserByEmail(String email);

    List<UserEntity> getAllUsers();

    UserEntity createUser(UserEntity userEntity);

    UserEntity getUserById(Long id);

    UserEntity updateUser(UserEntity reqUserEntity);

    // Reset Password
    GenericResponseDTO<Boolean> sendForgotPasswordResetEmail(ForgotPasswordRequestDTO forgotPasswordRequestDTO);

    GenericResponseDTO<Boolean> verifyAndProcessPasswordResetVerRequest(PasswordResetVerRequestDTO passwordResetVerRequestDTO);

}
