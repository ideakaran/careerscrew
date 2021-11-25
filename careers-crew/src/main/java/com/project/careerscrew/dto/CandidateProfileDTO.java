package com.project.careerscrew.dto;

import lombok.Data;

@Data
public class CandidateProfileDTO {
    private Long id;
    private String education;
    private Long candidateJobId;
    private String training;
    private String experience;
}
