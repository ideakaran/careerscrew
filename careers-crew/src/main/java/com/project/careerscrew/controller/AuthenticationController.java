package com.project.careerscrew.controller;

import com.project.careerscrew.dto.GenericResponseDTO;
import com.project.careerscrew.dto.auth.ForgotPasswordRequestDTO;
import com.project.careerscrew.dto.auth.PasswordResetVerRequestDTO;
import com.project.careerscrew.entities.UserEntity;
import com.project.careerscrew.services.UserService;
import com.project.careerscrew.services.auth.AuthenticationService;
import com.project.careerscrew.dto.auth.LoginRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        log.info("Authentication API: login requested by user: ", loginRequest.getEmail());
        UserEntity userEntity = authenticationService.loginUser(loginRequest);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @PostMapping("/send-forgot-password-email")
    public ResponseEntity<?> sendForgotPasswordResetEmail(@RequestBody ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
        log.info("Authentication API: sendForgotPasswordResetEmail: ", forgotPasswordRequestDTO.getEmail());
        GenericResponseDTO<Boolean> resendVerificationEmailStatus = userService.sendForgotPasswordResetEmail(forgotPasswordRequestDTO);
        return new ResponseEntity<>(resendVerificationEmailStatus, HttpStatus.OK);
    }

    @PostMapping("/process-password-update-request")
    public ResponseEntity<?> verifyAndProcessPasswordResetVerRequest(@RequestBody PasswordResetVerRequestDTO passwordResetVerRequestDTO) {
        log.info("Authentication API: verifyAndProcessPasswordResetVerRequest: ", passwordResetVerRequestDTO.getEmail());
        GenericResponseDTO<Boolean> checkVerificationCodeStatus = userService.verifyAndProcessPasswordResetVerRequest(passwordResetVerRequestDTO);
        return new ResponseEntity<>(checkVerificationCodeStatus, HttpStatus.OK);
    }
}
