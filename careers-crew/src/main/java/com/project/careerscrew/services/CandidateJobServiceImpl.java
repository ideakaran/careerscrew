package com.project.careerscrew.services;

import com.project.careerscrew.dto.CandidateJobDTO;
import com.project.careerscrew.entities.CandidateJob;
import com.project.careerscrew.entities.UserEntity;
import com.project.careerscrew.enums.ApplicationStatus;
import com.project.careerscrew.mapper.CandidateJobMapper;
import com.project.careerscrew.repository.CandidateJobRepository;
import com.project.careerscrew.repository.UserRepository;
import com.project.careerscrew.services.mail.EmailService;
import com.project.careerscrew.services.mail.MessageTemplateCodeUtil;
import com.project.careerscrew.utils.exceptions.AppExceptionConstants;
import com.project.careerscrew.utils.exceptions.CustomAppException;
import com.project.careerscrew.utils.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@AllArgsConstructor
@Service
public class CandidateJobServiceImpl implements CandidateJobService {

    private final CandidateJobRepository candidateJobRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final CandidateJobMapper candidateJobMapper;


    @Override
    public List<CandidateJob> getAllCandidateJobs() {
        return candidateJobRepository.findAll();
    }

    @Override
    public CandidateJob getCandidateJobById(Long id) {
        return candidateJobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppExceptionConstants.REQUESTED_RESOURCE_NOT_FOUND));
    }

    @Override
    public CandidateJobDTO getCandidateCurrentJobByUserId(Long userId) {
        CandidateJob candidateJob = candidateJobRepository.getCandidateJobByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(AppExceptionConstants.REQUESTED_RESOURCE_NOT_FOUND));
        CandidateJobDTO candidateJobDTO = candidateJobMapper.toDto(candidateJob);
        if (StringUtils.hasText(candidateJobDTO.getGithubLink())) {
            candidateJobDTO.setGithubLinks(Arrays.asList(candidateJob.getGithubLink().split(",")));
        }
        return candidateJobDTO;
    }

    @Override
    public void sendAssignment(CandidateJobDTO candidateJobDTO) {
        Optional<CandidateJob> optionalCandidateJob = candidateJobRepository.findById(candidateJobDTO.getId());
        if (optionalCandidateJob.isPresent()) {
            CandidateJob candidateJob = optionalCandidateJob.get();
            candidateJob.setAssignment(candidateJobDTO.getAssignment());
            candidateJobRepository.save(candidateJob);
            if (StringUtils.hasText(candidateJob.getAssignment())) {
                sendAssignmentMailToUser(candidateJob);
            }
        }
    }

    private void sendAssignmentMailToUser(CandidateJob candidateJob) {
        UserEntity user = candidateJob.getUserEntity();
        if (user != null) {
            Map<String, Object> templateMap = new HashMap<>();
            templateMap.put("fullName", user.getFullName());
            templateMap.put("description", candidateJob.getAssignment());
            templateMap.put("assessmentTime", LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            emailService.sendMessageUsingFreemarkerTemplate(user.getEmail(), "Code Assignment", templateMap, MessageTemplateCodeUtil.TemplatesPath.CANDIDATE_INITIAL_SELECTION_PROJECT);
        }
    }

    @Override
    public void submitResponse(CandidateJobDTO candidateJobDTO) {
        Optional<CandidateJob> candidateJob = candidateJobRepository.findById(candidateJobDTO.getId());
        if (candidateJob.isPresent()) {
            CandidateJob appliedJob = candidateJob.get();
            appliedJob.setGithubLink(candidateJobDTO.getGithubLink());
            candidateJobRepository.save(appliedJob);
        }
    }

    @Override
    public CandidateJob rejectApplication(Long candidateJobId) {
        CandidateJob candidateJob = candidateJobRepository.findById(candidateJobId)
                .orElseThrow(() -> new CustomAppException(AppExceptionConstants.REQUESTED_RESOURCE_NOT_FOUND));
        candidateJob.setApplicationStatus(ApplicationStatus.UNSELECTED);
        candidateJobRepository.save(candidateJob);
        return candidateJob;
    }
}
