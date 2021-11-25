package com.project.careerscrew.services;

import com.project.careerscrew.dto.CandidateInterviewDTO;
import com.project.careerscrew.dto.CandidateProfileDTO;
import com.project.careerscrew.dto.CandidateRemarkDTO;
import com.project.careerscrew.dto.KpiDTO;
import com.project.careerscrew.entities.CandidateJob;
import com.project.careerscrew.entities.DashboardBoxResponse;

public interface CandidateService {

    CandidateProfileDTO saveCandidateProfile(CandidateProfileDTO candidateProfileDTO);

    CandidateInterviewDTO saveCandidateInterview(CandidateInterviewDTO candidateInterviewDTO);

    CandidateRemarkDTO saveCandidateRemark(CandidateRemarkDTO candidateRemarkDTO);

    CandidateProfileDTO getCandidateProfileByCandidateJobId(Long id);

    CandidateInterviewDTO getCandidateInterviewByCandidateJobId(Long id);

    CandidateRemarkDTO getCandidateRemarkByCandidateJobId(Long id);

    void sendEmailNotificationToInterviewers(CandidateJob candidateJob, CandidateInterviewDTO candidateInterviewDTO, String interviewTime);

    DashboardBoxResponse getRecentHiresByFilter();

    KpiDTO getKpiValues();
}

