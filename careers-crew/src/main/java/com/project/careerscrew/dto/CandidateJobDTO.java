package com.project.careerscrew.dto;

import com.project.careerscrew.enums.ApplicationStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CandidateJobDTO {
    private Long id;
//    private Long userId;
//    private Long jobId;
    private UserEntityDTO userEntity;
    private JobDTO job;
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
    private String assignment;
    private String githubLink;
    private List<String> githubLinks;
}
