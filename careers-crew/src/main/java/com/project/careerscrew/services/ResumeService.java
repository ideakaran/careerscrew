package com.project.careerscrew.services;

import com.project.careerscrew.dto.CandidateJobDTO;
import com.project.careerscrew.dto.ResumeDTO;
import com.project.careerscrew.dto.ResumeRequest;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {
    ResumeDTO apply(ResumeRequest resumeRequest, MultipartFile file);
    void verifyReferral(Long verifyReferral, String code);
    void update(CandidateJobDTO candidateDTO);
}
