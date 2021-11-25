package com.project.careerscrew.dto.auth;

import lombok.Data;

@Data
public class PasswordResetVerRequestDTO {

    private String email;

    private String forgotPasswordVerCode;

    private String newPassword;
}
