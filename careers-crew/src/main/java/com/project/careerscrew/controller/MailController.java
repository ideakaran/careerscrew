package com.project.careerscrew.controller;

import com.project.careerscrew.dto.CandidateJobDTO;
import com.project.careerscrew.services.ResumeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MailController {
    private final ResumeService resumeService;

    /**
     *
     * @param candidateJobDTO
     * Payload For postman:
     * url: localhost:9090/update-candidate-job-status
     * {
     *     "description": "Test",
     *     "id": 1,
     *     "applicationStatus": "SELECTED",
     *     "userId": 2
     * }
     */

    @PostMapping("/update-candidate-job-status")
    public void sendNotifications(@RequestBody CandidateJobDTO candidateJobDTO) {
        resumeService.update(candidateJobDTO);
    }
}
