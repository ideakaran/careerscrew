package com.project.careerscrew.entities;

import com.project.careerscrew.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CandidateJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
    private LocalDateTime appliedDate;
    @Enumerated(value = EnumType.STRING)
    private ApplicationStatus applicationStatus;
    private String refererFullName;
    private String refererEmail;
    private Boolean isMailSentToReferrer;
    private Boolean isVerifiedByReferer;
    private String referralCode;
    private String description;
    private LocalDateTime scheduleInterviewDate;
    private LocalDateTime probationCompletionDate;
    private Boolean isEligibleForProbation = false;
    private Boolean hasSentProbationCompletionEmail = false;
    @Column(columnDefinition = "TEXT")
    private String assignment;
    private String githubLink;

    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived = false;
}
