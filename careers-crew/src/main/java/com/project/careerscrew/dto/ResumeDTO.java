package com.project.careerscrew.dto;

import lombok.Data;

@Data
public class ResumeDTO {
    private UserEntityDTO userEntityDTO;
    private CandidateJobDTO candidateJobDTO;
    private CandidateFileDTO candidateFileDTO;
}
