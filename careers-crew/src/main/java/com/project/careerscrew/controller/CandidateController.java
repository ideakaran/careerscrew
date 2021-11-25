package com.project.careerscrew.controller;

import com.project.careerscrew.dto.CandidateInterviewDTO;
import com.project.careerscrew.dto.CandidateProfileDTO;
import com.project.careerscrew.dto.CandidateRemarkDTO;
import com.project.careerscrew.services.CandidateService;
import com.project.careerscrew.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping(value = "/profile")
    public ResponseEntity<?> saveCandidateProfile(@RequestBody CandidateProfileDTO candidateProfileDTO) {
        return new Response().success(candidateService.saveCandidateProfile(candidateProfileDTO));
    }

    @PostMapping(value = "/interview")
    public ResponseEntity<?> saveCandidateInterview(@RequestBody CandidateInterviewDTO candidateInterviewDTO) {
        return new Response().success(candidateService.saveCandidateInterview(candidateInterviewDTO));
    }

    @PostMapping(value = "/remark")
    public ResponseEntity<?> saveCandidateRemark(@RequestBody CandidateRemarkDTO candidateRemarkDTO) {
        return new Response().success(candidateService.saveCandidateRemark(candidateRemarkDTO));
    }

    @GetMapping(value = "/profile/{candidateJobId}")
    public ResponseEntity<?> getCandidateProfile(@PathVariable("candidateJobId") Long id) {
        return new Response().success(candidateService.getCandidateProfileByCandidateJobId(id));
    }

    @GetMapping(value = "/interview/{candidateJobId}")
    public ResponseEntity<?> getCandidateInterview(@PathVariable("candidateJobId") Long id) {
        return new Response().success(candidateService.getCandidateInterviewByCandidateJobId(id));
    }

    @GetMapping(value = "/remark/{candidateJobId}")
    public ResponseEntity<?> getCandidateRemark(@PathVariable("candidateJobId") Long id) {
        return new Response().success(candidateService.getCandidateRemarkByCandidateJobId(id));
    }

    @GetMapping(value = "/getRecentHire")
    public ResponseEntity<?> getRecentHires() {
        return new Response().success(candidateService.getRecentHiresByFilter());
    }
    @GetMapping(value = "/getKpiValues")
    public ResponseEntity<?> getKpiValues() {
        return new Response().success(candidateService.getKpiValues());
    }
}
