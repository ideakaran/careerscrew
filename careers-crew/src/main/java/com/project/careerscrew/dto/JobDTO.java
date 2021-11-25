package com.project.careerscrew.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class JobDTO {
    private Long id;
    @NotBlank(message = "please include to job title")
    private String title;
    private String position;
    private String description;
    @JsonProperty
    private Boolean isActive;
}
