package com.project.careerscrew.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Data
public class CandidateInterviewDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer stage;
    private LocalDateTime interviewDate;
    private String description;
    private Long candidateJobId;
    protected Set<Long> interviewersId = new HashSet<>();
}
