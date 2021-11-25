package com.project.careerscrew.services;

import com.project.careerscrew.dto.InterviewerDTO;

import java.util.List;

public interface InterviewerService {
    InterviewerDTO save(InterviewerDTO interviewerDTO);

    List<InterviewerDTO> getAllInterviewers();
}
