package com.project.careerscrew.controller;

import com.project.careerscrew.dto.JobDTO;
import com.project.careerscrew.services.JobService;
import com.project.careerscrew.utils.FieldValidationService;
import com.project.careerscrew.utils.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/jobs")
@AllArgsConstructor
@Slf4j
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
public class JobController {

    private final FieldValidationService fieldValidationService;
    private final JobService jobService;

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody JobDTO jobDTO, BindingResult result) {
        ResponseEntity<?> errorMap = fieldValidationService.validateField(result);
        if (errorMap != null) {
            return errorMap;
        }
        return new Response().success(jobService.save(jobDTO));
    }

    @GetMapping("all")
    public ResponseEntity<?> all() {
        return new Response().success(jobService.getAllJobs());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        log.info("Job API: getJobById");
        ResponseEntity<?> success = new Response().success(jobService.getJobById(id));
        return success;
    }

    @PutMapping
    public ResponseEntity<?> updateJob(@Valid @RequestBody JobDTO jobDTO, BindingResult result) {
        log.info("Job API: updateJob");
        ResponseEntity<?> errorMap = fieldValidationService.validateField(result);
        if (errorMap != null) {
            return errorMap;
        }
        return new Response().success(jobService.updateJob(jobDTO));
    }
}
