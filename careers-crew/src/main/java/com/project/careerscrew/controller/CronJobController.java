package com.project.careerscrew.controller;


import com.project.careerscrew.config.AppProperties;
import com.project.careerscrew.entities.CandidateJob;
import com.project.careerscrew.repository.CandidateJobRepository;
import com.project.careerscrew.services.mail.EmailService;
import com.project.careerscrew.services.mail.MessageTemplateCodeUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@EnableScheduling
public class CronJobController {
    @Autowired
   EmailService emailService;
    @Autowired
    AppProperties appProperties;
   @Autowired
   CandidateJobRepository candidateJobRepository;
   @Scheduled(cron = "0/20 * * * * ?")
    public void execute() {
        List<CandidateJob> candidateJobs = candidateJobRepository.findAll();
        List<CandidateJob> emailCandidates = new ArrayList<>();
        for(CandidateJob candidateJob : candidateJobs) {
            if(!ObjectUtils.isEmpty(candidateJob.getRefererEmail())) {
                if(candidateJob.getProbationCompletionDate().isAfter(LocalDateTime.now())) {
                    if(!candidateJob.getHasSentProbationCompletionEmail() && candidateJob.getIsEligibleForProbation()) {
                        candidateJob.setHasSentProbationCompletionEmail(true);
                        candidateJob.setIsEligibleForProbation(false);
                        emailCandidates.add(candidateJob);
                        sendEmailToReferer(candidateJob);
                    }
                }
            }
        }
        if(emailCandidates.size() > 0) {
            candidateJobRepository.saveAll(emailCandidates);
        }
    }


    public  void sendEmailToReferer(CandidateJob candidateJob) {
        Map<String, Object> templateMap = new HashMap<>();
        templateMap.put("fullName", candidateJob.getRefererFullName());
        templateMap.put("refererEmail", candidateJob.getRefererEmail());
        templateMap.put("refererName", candidateJob.getRefererFullName());
        templateMap.put("applicantEmail", candidateJob.getUserEntity().getEmail());
        templateMap.put("jobTitle", candidateJob.getJob().getTitle());
        emailService.sendMessageUsingFreemarkerTemplate(candidateJob.getRefererEmail(),
                "Congratulations", templateMap,
                MessageTemplateCodeUtil.TemplatesPath.REFERER_BONUS_TEMPLATE);

        //replace hardcoded email by company hr email
        emailService.sendMessageUsingFreemarkerTemplate(appProperties.getDefaults().getInitialAdminEmail(),
                "Employee Referrel Bonus to "+candidateJob.getRefererFullName(), templateMap,
                MessageTemplateCodeUtil.TemplatesPath.EMAIL_HR_FOR_REFERER_BONUS);
    }


}
