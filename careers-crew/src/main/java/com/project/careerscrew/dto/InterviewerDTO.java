package com.project.careerscrew.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InterviewerDTO {
    private Long id;
    @NotBlank(message = "please include interviewer name")
    private String name;
    @NotBlank(message = "Please include interviewer email")
    private String email;
}
