package com.project.careerscrew.dto;

import com.project.careerscrew.enums.ApplicationStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
public class ResumeRequest {
    private String fullName;
    private String email;
    private String referenceId;
    private String contactNumber;
    private Long userId;
    private Long jobId;
    private LocalDateTime appliedDate;
    @Enumerated(value = EnumType.STRING)
    private ApplicationStatus applicationStatus;
    private String refererFullName;
    private String refererEmail;
    private String path;
    private String fileName;
    private Long candidateJobId;
}
