package com.project.careerscrew.dto;

import lombok.Data;

@Data
public class CandidateRemarkDTO {
    private Long id;
    private String remarks;
    private String tackling;
    private String communication;
    private String stability;
    private String grace;
    private String writtenTestScore;
    private Boolean isSuitableForHiring;
    private String personality;
    private String knowledge;
    private Long candidateJobId;
}
