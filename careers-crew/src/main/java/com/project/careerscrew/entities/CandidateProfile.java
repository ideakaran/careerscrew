package com.project.careerscrew.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CandidateProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String education;
    @OneToOne
    @JoinColumn(name = "candidate_job_id")
    private CandidateJob candidateJob;
    private String training;
    private String experience;
}
