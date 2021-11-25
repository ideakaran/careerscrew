package com.project.careerscrew.dto;

import lombok.Data;

@Data
public class CandidateFileDTO {
    private Long id;
    private Long candidateJobId;
    private String fileName;
    private Boolean isPrimaryResume;
    private String path;
}
