package com.project.careerscrew.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CandidateFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_job_id")
    private CandidateJob candidateJob;

    private String fileName;
    @Column(updatable = false)

    @CreationTimestamp
    private LocalDateTime uploadDate;

    private Boolean isPrimaryResume;

    private String path;
}
