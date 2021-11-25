package com.project.careerscrew.services;

import com.project.careerscrew.dto.InterviewerDTO;
import com.project.careerscrew.mapper.InterviewerMapper;
import com.project.careerscrew.repository.InterviewerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class InterviewerServiceImpl implements InterviewerService{

    private final InterviewerRepository interviewerRepository;
    private final InterviewerMapper interviewerMapper;

    @Override
    public InterviewerDTO save(InterviewerDTO interviewerDTO) {
        return interviewerMapper.toDto(interviewerRepository.save(interviewerMapper.toEntity(interviewerDTO)));
    }

    @Override
    public List<InterviewerDTO> getAllInterviewers() {
        return interviewerMapper.toDto(interviewerRepository.findAll());
    }
}
