package com.project.careerscrew.controller;

import com.project.careerscrew.entities.UserEntity;
import com.project.careerscrew.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUser() {
        log.info("User API: getAllUser");
        List<UserEntity> userDTOList = userService.getAllUsers();
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER') or #id == authentication.principal.userId")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        log.info("User API: getUserById: ", id);
        UserEntity userEntity = userService.getUserById(id);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity) {
        log.info("User API: updateUser");
        UserEntity returnedUserEntity = userService.updateUser(userEntity);
        return new ResponseEntity<>(returnedUserEntity, HttpStatus.OK);
    }

}
