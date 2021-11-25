package com.project.careerscrew.controller;

import com.project.careerscrew.services.InterviewerService;
import com.project.careerscrew.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interviewer")
@AllArgsConstructor
public class InterviewerController {
    private final InterviewerService interviewerService;
    @GetMapping("/all")
    public ResponseEntity<?> getInterviewers() {
        return new Response().success(interviewerService.getAllInterviewers());
    }
}
