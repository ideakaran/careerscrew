package com.project.careerscrew.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CandidateRemark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @OneToOne
    @JoinColumn(name = "candidate_job_id")
    private CandidateJob candidateJob;
}
