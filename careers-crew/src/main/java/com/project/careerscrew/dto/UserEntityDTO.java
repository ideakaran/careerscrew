package com.project.careerscrew.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserEntityDTO {
    private Long id;
    @NotBlank(message = "Please enter full name ")
    private String fullName;
    @Email(message = "Email cannot be empty")
    private String email;
    private String referenceId;
    @NotBlank(message = "Please enter contact number")
    private String contactNumber;
    @JsonIgnore
    private String password;
}
