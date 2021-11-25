package com.project.careerscrew.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CandidateInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer stage;
    private LocalDateTime interviewDate;
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToOne
    @JoinColumn(name = "candidate_job_id")
    private CandidateJob candidateJob;
    @ManyToMany
    @JoinTable(
            name = "candidate_interviewers",
            joinColumns = {@JoinColumn(name = "candidate_interview_id")},
            inverseJoinColumns = {@JoinColumn(name = "interviewer_id")})
    protected Set<Interviewer> interviewers = new HashSet<>();
}
