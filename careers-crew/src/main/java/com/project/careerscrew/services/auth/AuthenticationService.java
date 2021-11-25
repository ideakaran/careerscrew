package com.project.careerscrew.services.auth;

import com.project.careerscrew.dto.auth.LoginRequestDTO;
import com.project.careerscrew.entities.UserEntity;

public interface AuthenticationService {

    UserEntity loginUser(LoginRequestDTO loginRequest);


}
