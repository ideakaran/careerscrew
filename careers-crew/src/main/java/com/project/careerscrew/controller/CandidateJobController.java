package com.project.careerscrew.controller;

import com.project.careerscrew.dto.CandidateJobDTO;
import com.project.careerscrew.entities.CandidateJob;
import com.project.careerscrew.services.CandidateJobService;
import com.project.careerscrew.utils.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/candidate-jobs")
@AllArgsConstructor
@Slf4j
public class CandidateJobController {

    private final CandidateJobService candidateJobService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAllCandidateJobs() {
        log.info("CandidateJob API: getAllCandidateJobs");
        List<CandidateJob> candidateJobs = candidateJobService.getAllCandidateJobs();
        return new ResponseEntity<>(candidateJobs, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER') or #userId == authentication.principal.userId")
    @GetMapping("/current/{userId}")
    public ResponseEntity<?> getCandidateCurrentJobByUserId(@PathVariable Long userId) {
        log.info("User API: getCandidateCurrentJobByUserId: ", userId);
        return new ResponseEntity<>(candidateJobService.getCandidateCurrentJobByUserId(userId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping("/send-assignment")
    public ResponseEntity<?> sendAssignment(@RequestBody CandidateJobDTO candidateJobDTO) {
        candidateJobService.sendAssignment(candidateJobDTO);
        return new Response().success("Send Successfully!!!");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping("/submit-response")
    public ResponseEntity<?> sendResponse(@RequestBody CandidateJobDTO candidateJobDTO) {
        candidateJobService.submitResponse(candidateJobDTO);
        return new Response().success("Send Successfully!!!");
    }

    @PostMapping("/submit-solution")
    public ResponseEntity<?> submitSolution(@RequestBody Map<String, Object> data) {
        Map<String, Object> solutions = (Map<String, Object>) data.get("solutions");
        List<String> solutionLink = (List<String>) solutions.get("solutions");
        CandidateJobDTO candidateJobDTO = new CandidateJobDTO();
        candidateJobDTO.setGithubLink(String.join(",", solutionLink));
        candidateJobDTO.setId(Long.valueOf((Integer) data.get("candidateJobId")));
        candidateJobService.submitResponse(candidateJobDTO);
        return new Response().success("Send Successfully!!!");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping("/reject-application")
    public ResponseEntity<?> rejectApplication(@RequestBody CandidateJobDTO candidateJobDTO) {
        CandidateJob candidateJob = candidateJobService.rejectApplication(candidateJobDTO.getId());
        return new Response().success(candidateJob);
    }
}
