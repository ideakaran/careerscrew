package com.project.careerscrew.services;

import com.project.careerscrew.config.AppProperties;
import com.project.careerscrew.dto.*;
import com.project.careerscrew.entities.CandidateFile;
import com.project.careerscrew.entities.CandidateJob;
import com.project.careerscrew.entities.UserEntity;
import com.project.careerscrew.enums.ApplicationStatus;
import com.project.careerscrew.mapper.CandidateFileMapper;
import com.project.careerscrew.mapper.CandidateJobMapper;
import com.project.careerscrew.mapper.ResumeMapper;
import com.project.careerscrew.mapper.UserMapper;
import com.project.careerscrew.repository.CandidateFileRepository;
import com.project.careerscrew.repository.CandidateJobRepository;
import com.project.careerscrew.repository.JobRepository;
import com.project.careerscrew.repository.UserRepository;
import com.project.careerscrew.security.UserRole;
import com.project.careerscrew.services.mail.EmailService;
import com.project.careerscrew.services.mail.MessageTemplateCodeUtil;
import com.project.careerscrew.utils.FileUploadUtils;
import com.project.careerscrew.utils.PasswordGenerationUtil;
import com.project.careerscrew.utils.exceptions.AppExceptionConstants;
import com.project.careerscrew.utils.exceptions.CustomAppException;
import com.project.careerscrew.utils.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class ResumeServiceImpl implements ResumeService {
    private final UserRepository userRepository;
    private final CandidateJobRepository candidateJobRepository;
    private final CandidateFileRepository candidateFileRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AppProperties appProperties;
    private final JobRepository jobRepository;
    private final CandidateFileMapper candidateFIleMapper;
    private final EntityManager entityManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResumeDTO apply(ResumeRequest resumeRequest, MultipartFile multipartFile) {
        uploadFileAndPopulateResumeRequest(resumeRequest, multipartFile);
        ResumeDTO returnValue = new ResumeDTO();
        ResumeDTO resumeDTO = ResumeMapper.INSTANCE.toDto(resumeRequest);
        String generatedUserPassword = PasswordGenerationUtil.generateRandom();
        UserEntityDTO savedUserEntity = saveUserEntity(resumeDTO.getUserEntityDTO(), generatedUserPassword);

        resumeDTO.getCandidateJobDTO().getUserEntity().setId((savedUserEntity.getId()));
        CandidateJob candidateJob = saveCandidateJob(resumeDTO.getCandidateJobDTO());
        resumeDTO.getCandidateFileDTO().setCandidateJobId(candidateJob.getId());
        CandidateFileDTO candidateFileDTO = saveCandidateFile(resumeDTO.getCandidateFileDTO());

        // Send Email to candidate & referer and prepare response
        sendApplicationSubmittedEmailToUser(candidateJob, generatedUserPassword);
        if (StringUtils.hasText(resumeRequest.getEmail()) && StringUtils.hasText(resumeRequest.getRefererFullName())) {
            sendEmailToReferer(candidateJob, savedUserEntity);
        }
        CandidateJobDTO candidateJobDTO = CandidateJobMapper.INSTANCE.toDto(candidateJob);
        returnValue.setUserEntityDTO(savedUserEntity);
        returnValue.setCandidateJobDTO(candidateJobDTO);
        returnValue.setCandidateFileDTO(candidateFileDTO);
        return returnValue;
    }

    @Override
    public void update(CandidateJobDTO candidateDTO) {
        Optional<CandidateJob> candidateJob = candidateJobRepository.findById(candidateDTO.getId());
        if (candidateJob.isPresent()) {
            if (candidateJob.get().getApplicationStatus() != candidateDTO.getApplicationStatus()) {
                candidateJob.get().setApplicationStatus(candidateDTO.getApplicationStatus());
                candidateJobRepository.save(candidateJob.get());

                String status = candidateJob.get().getApplicationStatus().getStatus();
                sendEmailNotification(candidateJob.get());

            }
        }
    }

    private void uploadFileAndPopulateResumeRequest(ResumeRequest resumeRequest, MultipartFile multipartFile) {
        String multiPartFileName = FileUploadUtils.createCleanFileNameForMultipartFile(multipartFile);
        FileUploadUtils.uploadFile(multipartFile, multiPartFileName);
        resumeRequest.setFileName(multiPartFileName);
        resumeRequest.setPath(multiPartFileName);
    }

    private void sendEmailNotification(CandidateJob candidateJob) {
        String status = candidateJob.getApplicationStatus().getStatus();

        UserEntity candidate = userRepository.findUserEntityById(candidateJob.getUserEntity().getId());
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("fullName", candidate.getFullName());
        templateModel.put("email", candidate.getEmail());
        templateModel.put("description", candidateJob.getDescription());
        templateModel.put("status", candidateJob.getApplicationStatus().getStatus());

        if (status == ApplicationStatus.INTERVIEW_SCHEDULED.getStatus()) {
            templateModel.put("interviewDate", candidateJob.getScheduleInterviewDate());
            emailService.sendMessageUsingFreemarkerTemplate(candidate.getEmail(), "Interview Scheduled",
                    templateModel, MessageTemplateCodeUtil.TemplatesPath.INTERVIEW_SCHEDULED_TEMPLATE);
        } else if (status == ApplicationStatus.SELECTED.getStatus()) {
            emailService.sendMessageUsingFreemarkerTemplate(candidate.getEmail(), "Interview Passed",
                    templateModel, MessageTemplateCodeUtil.TemplatesPath.INTERVIEW_SELECTION_STATUS_TEMPLATE);
        } else if (status == ApplicationStatus.UNSELECTED.getStatus()) {
            emailService.sendMessageUsingFreemarkerTemplate(candidate.getEmail(), "Interview Failed",
                    templateModel, MessageTemplateCodeUtil.TemplatesPath.INTERVIEW_SELECTION_STATUS_TEMPLATE);
        }
    }

    @Override
    public void verifyReferral(Long candidateJobId, String code) {
        CandidateJob candidateJob = candidateJobRepository.findByIdAndReferralCode(candidateJobId, code)
                .orElseThrow(() -> new ResourceNotFoundException(AppExceptionConstants.INVALID_REQUEST));
        candidateJob.setIsVerifiedByReferer(Boolean.TRUE);
        candidateJob.setReferralCode(null);
        candidateJobRepository.save(candidateJob);
    }

    private void sendApplicationSubmittedEmailToUser(CandidateJob candidateJob, String password) {
        Map<String, Object> templateMap = new HashMap<>();
        templateMap.put("fullName", candidateJob.getUserEntity().getFullName());
        templateMap.put("jobPosition", candidateJob.getJob().getTitle());
        String companyBrandName = StringUtils.hasText(appProperties.getCompanyBrandName()) ? appProperties.getCompanyBrandName() : appProperties.getFrontSite();
        templateMap.put("companyBrandName", companyBrandName);
        templateMap.put("trackingSite", appProperties.getFrontSite() + "/login");
        templateMap.put("userEmail", candidateJob.getUserEntity().getEmail());
        templateMap.put("generatedPass", password);
        emailService.sendMessageUsingFreemarkerTemplate(candidateJob.getUserEntity().getEmail(), "Application has been submitted", templateMap, MessageTemplateCodeUtil.TemplatesPath.RESUME_SUBMITTED_TEMPLATE);
    }

    private CandidateFileDTO saveCandidateFile(CandidateFileDTO candidateFileDTO) {
        CandidateFile candidateFile = candidateFIleMapper.toEntity(candidateFileDTO);
        candidateFile.setIsPrimaryResume(true);
        return candidateFIleMapper.toDto(candidateFileRepository.save(candidateFile));
    }

    private UserEntityDTO saveUserEntity(UserEntityDTO userEntityDTO, String generatedPassword) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(userEntityDTO.getEmail());

        // [Condition For: User re-applying, i.e if user is applying using same email address]
        boolean isReapplyingUser = false;
        if (optionalUserEntity.isPresent()) {
            // [Checking role. It must be of ROLE_USER ]
            boolean isUser = optionalUserEntity.get().getRoles().stream()
                    .allMatch(userRole -> userRole.equals(UserRole.ROLE_USER));
            if (!isUser) {
                throw new CustomAppException(AppExceptionConstants.THIS_EMAIL_IS_NOT_AVAILABLE_FOR_PROCESSING);
            }
            // If Applied Job status is Either SELECTED or UNSELECTED, set the job as archived and continue for new application
            // Else, throw exception stating: The application is currently under processing
            Optional<CandidateJob> candidateJob = candidateJobRepository.getCandidateJobByUserId(optionalUserEntity.get().getId());
            if (candidateJob.isPresent()) {
                CandidateJob cj = candidateJob.get();
                boolean statusCompleted = EnumSet.allOf(ApplicationStatus.class)
                        .stream()
                        .filter(s -> s.equals(ApplicationStatus.SELECTED) || s.equals(ApplicationStatus.UNSELECTED))
                        .anyMatch(completedStatusValue -> completedStatusValue.equals(cj.getApplicationStatus()));
                if (!statusCompleted) {
                    throw new CustomAppException(AppExceptionConstants.YOUR_APPLICATION_IS_UNDER_PROCESSING);
                }
                cj.setIsArchived(true);
                candidateJobRepository.save(cj);
            }
            isReapplyingUser = true;
        }
        UserEntity mappedUserEntity = UserMapper.INSTANCE.toEntity(userEntityDTO);
        if (isReapplyingUser) {
            mappedUserEntity = optionalUserEntity.get();
        }
        if (StringUtils.hasText(generatedPassword)) {
            mappedUserEntity.setPassword(passwordEncoder.encode(generatedPassword));
        } else {
            mappedUserEntity.setPassword(passwordEncoder.encode(userEntityDTO.getPassword()));
        }
        if (ObjectUtils.isEmpty(mappedUserEntity.getRoles())) {
            mappedUserEntity.setRoles(Set.of(UserRole.ROLE_USER));
        }
        mappedUserEntity.setContactNumber(userEntityDTO.getContactNumber());
        UserEntityDTO savedUserEntity = UserMapper.INSTANCE.toDto(userRepository.save(mappedUserEntity));
        return savedUserEntity;
    }

    private CandidateJob saveCandidateJob(CandidateJobDTO candidateJobDTO) {
        candidateJobDTO.setApplicationStatus(ApplicationStatus.SUBMITTED);
        String referralCode = PasswordGenerationUtil.generateRandom();
        candidateJobDTO.setReferralCode(referralCode);
        candidateJobDTO.setIsVerifiedByReferer(Boolean.FALSE);
        candidateJobDTO.setAppliedDate(LocalDateTime.now());
        CandidateJob candidateJob = candidateJobRepository.save(CandidateJobMapper.INSTANCE.toEntity(candidateJobDTO));
        entityManager.refresh(candidateJob);
        return candidateJob;
    }

    private void sendEmailToReferer(CandidateJob candidateJob, UserEntityDTO userEntityDTO) {
        Map<String, Object> templateMap = new HashMap<>();
        templateMap.put("fullName", candidateJob.getRefererFullName());
        templateMap.put("applicantEmail", userEntityDTO.getEmail());
        templateMap.put("applicantName", userEntityDTO.getFullName());
        templateMap.put("jobTitle", candidateJob.getJob().getTitle());
        String parseApiEndpointPathToHit = UriComponentsBuilder.fromUriString("/public-apis/referral/verify-candidate")
                .queryParam("candidateJobId", candidateJob.getId())
                .queryParam("referralCode", candidateJob.getReferralCode())
                .build().toUriString();
        String linkForRefererToVerifyCandidate = UriComponentsBuilder.fromUriString(appProperties.getFrontSite() + "/verify")
                .queryParam("apiEndpointToHit", URLEncoder.encode(parseApiEndpointPathToHit, StandardCharsets.UTF_8))
                .build().toUriString();
        templateMap.put("linkForRefererToVerifyCandidate", linkForRefererToVerifyCandidate);
        boolean isMailSentToReferer = emailService.sendMessageUsingFreemarkerTemplate(candidateJob.getRefererEmail(), "Please verify your referred candidate", templateMap, MessageTemplateCodeUtil.TemplatesPath.VERIFY_REFERRAL_TEMPLATE, true);
        candidateJob.setIsMailSentToReferrer(isMailSentToReferer);
        candidateJobRepository.save(candidateJob);
    }
}
