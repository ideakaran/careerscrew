package com.project.careerscrew.services;

import com.project.careerscrew.config.AppProperties;
import com.project.careerscrew.dto.CandidateInterviewDTO;
import com.project.careerscrew.dto.CandidateProfileDTO;
import com.project.careerscrew.dto.CandidateRemarkDTO;
import com.project.careerscrew.dto.KpiDTO;
import com.project.careerscrew.entities.*;
import com.project.careerscrew.enums.ApplicationStatus;
import com.project.careerscrew.mapper.CandidateInterviewMapper;
import com.project.careerscrew.mapper.CandidateProfileMapper;
import com.project.careerscrew.mapper.CandidateRemarkMapper;
import com.project.careerscrew.services.mail.EmailService;
import com.project.careerscrew.services.mail.MessageTemplateCodeUtil;
import com.project.careerscrew.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService{
    private final CandidateInterviewRepository candidateInterviewRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final CandidateRemarkRepository candidateRemarkRepository;
    private final CandidateRemarkMapper candidateRemarkMapper;
    private final CandidateProfileMapper candidateProfileMapper;
    private final CandidateInterviewMapper candidateInterviewMapper;
    private final CandidateJobRepository candidateJobRepository;
    private final JobRepository jobRepository;
    private final EmailService emailService;
    private final AppProperties appProperties;
    private final InterviewerRepository interviewerRepository;

    @Override
    public CandidateProfileDTO saveCandidateProfile(CandidateProfileDTO candidateProfileDTO) {
        return candidateProfileMapper.toDto(candidateProfileRepository.save(candidateProfileMapper.toEntity(candidateProfileDTO)));
    }

    // TODO
    @Override
    public CandidateInterviewDTO saveCandidateInterview(CandidateInterviewDTO candidateInterviewDTO) {

        Optional<CandidateJob> candidateJob = candidateJobRepository.findById(candidateInterviewDTO.getCandidateJobId());
        if(candidateJob.isPresent()){
            UserEntity candidate = candidateJob.get().getUserEntity();
            candidateJob.get().setApplicationStatus(ApplicationStatus.INTERVIEW_SCHEDULED);
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("fullName", candidate.getFullName());
            templateModel.put("email", candidate.getEmail());
            templateModel.put("description", candidateInterviewDTO.getDescription());
            templateModel.put("jobPosition", candidateJob.get().getJob().getTitle());
            templateModel.put("interviewTime", getInterviewTime(candidateInterviewDTO.getInterviewDate()));
            candidateJobRepository.save(candidateJob.get());
            emailService.sendMessageUsingFreemarkerTemplate(candidate.getEmail(), "Call For Interview!",
                        templateModel, MessageTemplateCodeUtil.TemplatesPath.INTERVIEW_SCHEDULED_TEMPLATE);
            // for interviewers
            this.sendEmailNotificationToInterviewers(candidateJob.get(), candidateInterviewDTO, getInterviewTime(candidateInterviewDTO.getInterviewDate()));
        }
        return candidateInterviewMapper.toDto(candidateInterviewRepository.save(candidateInterviewMapper.toEntity(candidateInterviewDTO)));
    }

    public String getInterviewTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = time.format(formatter);
        return formattedDateTime;
    }

    @Override
    public CandidateRemarkDTO saveCandidateRemark(CandidateRemarkDTO candidateRemarkDTO) {
        Optional<CandidateJob> candidateJob = candidateJobRepository.findById(candidateRemarkDTO.getCandidateJobId());
        if(candidateJob.isPresent()){
            UserEntity candidate = candidateJob.get().getUserEntity();
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("fullName", candidate.getFullName());
            templateModel.put("email", candidate.getEmail());
            templateModel.put("jobTitle", candidateJob.get().getJob().getTitle());
            templateModel.put("refererName", candidate.getEmail());
            if (candidateRemarkDTO.getIsSuitableForHiring()){
            candidateJob.get().setApplicationStatus(ApplicationStatus.SELECTED);
            candidateJob.get().setProbationCompletionDate(LocalDateTime.now().plusDays(appProperties.getDefaults().getProbationCompletionDays()));
            candidateJob.get().setIsEligibleForProbation(true);
                sendEmailNotification(candidate.getEmail(), "Congratulations!", templateModel, MessageTemplateCodeUtil.TemplatesPath.INTERVIEW_SELECTION_STATUS_TEMPLATE);
                if (StringUtils.hasText(candidateJob.get().getRefererEmail())) {
                    sendEmailNotification(candidateJob.get().getRefererEmail(), "Congratulations!", templateModel, MessageTemplateCodeUtil.TemplatesPath.INTERVIEW_SELECTION_REFFERER_STATUS_TEMPLATE);
                }
                sendEmailNotification(appProperties.getDefaults().getInitialAdminEmail(), "Candidate Passed Interview Round!", templateModel, MessageTemplateCodeUtil.TemplatesPath.EMAIL_HR_AS_INTERVIEW_PASSED);
            } else {
                candidateJob.get().setApplicationStatus(ApplicationStatus.UNSELECTED);
                sendEmailNotification(candidate.getEmail(), "Interview Failed!", templateModel, MessageTemplateCodeUtil.TemplatesPath.INTERVIEW_UNSELECTION_STATUS_TEMPLATE);
                if (StringUtils.hasText(candidateJob.get().getRefererEmail())) {
                    sendEmailNotification(candidateJob.get().getRefererEmail(), "Interview Failed!", templateModel, MessageTemplateCodeUtil.TemplatesPath.INTERVIEW_UNSELECTION_REFFERER_STATUS_TEMPLATE);
                }
                sendEmailNotification(appProperties.getDefaults().getInitialAdminEmail(), "Candidate Failed Interview Round!", templateModel, MessageTemplateCodeUtil.TemplatesPath.EMAIL_HR_AS_INTERVIEW_FAILED);

            }
            candidateJobRepository.save(candidateJob.get());
        }

        return candidateRemarkMapper.toDto(candidateRemarkRepository.save(candidateRemarkMapper.toEntity(candidateRemarkDTO)));
    }

    public void sendEmailNotification(String email, String subject, Map<String, Object> templateModel, MessageTemplateCodeUtil.TemplatesPath templatesPath) {
        emailService.sendMessageUsingFreemarkerTemplate(email, subject, templateModel, templatesPath);
    }

    @Override
    public CandidateProfileDTO getCandidateProfileByCandidateJobId(Long id) {
        CandidateProfile candidateProfile = candidateProfileRepository.getCandidateProfileByCandidateJobId(id);
        if(candidateProfile!=null){
            return candidateProfileMapper.toDto(candidateProfile);
        }
        return null;
    }

    @Override
    public CandidateInterviewDTO getCandidateInterviewByCandidateJobId(Long id) {
        CandidateInterview candidateInterview = candidateInterviewRepository.getCandidateInterviewByCandidateJobId(id);
        if(candidateInterview!=null){
            return candidateInterviewMapper.toDto(candidateInterview);
        }
        return null;
    }

    @Override
    public CandidateRemarkDTO getCandidateRemarkByCandidateJobId(Long id) {
        CandidateRemark candidateRemark = candidateRemarkRepository.getCandidateRemarkByCandidateJobId(id);
        if(candidateRemark!=null){
            return candidateRemarkMapper.toDto(candidateRemark);
        }
        return null;
    }

    @Override
    public void sendEmailNotificationToInterviewers(CandidateJob candidateJob, CandidateInterviewDTO candidateInterviewDTO, String interviewTime) {
        Set<Long> interviewers = candidateInterviewDTO.getInterviewersId();
        List<Interviewer> interviewerList = interviewerRepository.findAllById(interviewers);
        UserEntity userEntity = candidateJob.getUserEntity();
        for (Interviewer interviewer : interviewerList) {
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("fullName", interviewer.getName());
            templateModel.put("email", interviewer.getEmail());
            templateModel.put("description", candidateInterviewDTO.getDescription());
            templateModel.put("applicantName", userEntity.getFullName());
            templateModel.put("applicantEmail", userEntity.getEmail());
            templateModel.put("jobPosition", candidateJob.getJob().getTitle());
            templateModel.put("profile", "http://localhost:4200/dashboard/user-details/"+userEntity.getId());
            templateModel.put("interviewTime", interviewTime);
            emailService.sendMessageUsingFreemarkerTemplate(interviewer.getEmail(), "Call For Interview!",
                    templateModel, MessageTemplateCodeUtil.TemplatesPath.EMAIL_INTERVIEWER_FOR_INTERVIEW);
        }

    }

    @Override
    public DashboardBoxResponse getRecentHiresByFilter() {
        //add filter support, Assuming fiter is null for now
        DashboardBoxResponse response = new DashboardBoxResponse();
        List<CandidateJob> candidateJobs = candidateJobRepository.findAll();
        if(candidateJobs != null && candidateJobs.size() > 0) {
            List<CandidateJob> result = new ArrayList<>();
            for (CandidateJob job: candidateJobs) {
                //todo check date as well
                if(job.getApplicationStatus().getStatus() == ApplicationStatus.SELECTED.getStatus()) {
                    result.add(job);
                }
            }
            response.setCount(result.size());
            response.setLabel("Recent Hires");
            response.setData(result);
        }
        return response;
    }

    @Override
    public KpiDTO getKpiValues() {
        KpiDTO kpiDTO = new KpiDTO();
        kpiDTO.setRecentHires(candidateJobRepository.countByApplicationStatus(ApplicationStatus.SELECTED));
        kpiDTO.setActiveRefferals(candidateJobRepository.countByIsVerifiedByRefererAndApplicationStatus(true, ApplicationStatus.SELECTED));
        kpiDTO.setActiveVacancies(jobRepository.countByIsActive(true));
        kpiDTO.setNewCandidate(candidateJobRepository.countByApplicationStatus(ApplicationStatus.SUBMITTED));
        return kpiDTO;
    }
}
