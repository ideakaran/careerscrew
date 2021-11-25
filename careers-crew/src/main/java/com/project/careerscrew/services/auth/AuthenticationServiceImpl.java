package com.project.careerscrew.services.auth;

import com.project.careerscrew.dto.auth.LoginRequestDTO;
import com.project.careerscrew.entities.UserEntity;
import com.project.careerscrew.services.UserService;
import com.project.careerscrew.utils.exceptions.AppExceptionConstants;
import com.project.careerscrew.utils.exceptions.CustomAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public UserEntity loginUser(LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword()));
            UserEntity userEntity = userService.findUserByEmail(loginRequest.getEmail());
            if (authentication == null) {
                throw new CustomAppException(AppExceptionConstants.INTERNAL_EXCEPTION);
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return userEntity;
        } catch (AuthenticationException e) {
            log.error("BadCredentialsException handled {}", e.getMessage());
            throw new BadCredentialsException(e.getMessage());
        }
    }
}
