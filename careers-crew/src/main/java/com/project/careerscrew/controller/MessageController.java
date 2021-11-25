package com.project.careerscrew.controller;

import com.project.careerscrew.services.messaging.MessageRequestDTO;
import com.project.careerscrew.services.messaging.MessageService;
import com.project.careerscrew.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
public class MessageController {

    private final MessageService messageService;

    @PostMapping(value = "/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequestDTO messageRequestDTO) {
        return new Response().success(messageService.sendMessage(messageRequestDTO));
    }

    @PostMapping(value = "/reply")
    public ResponseEntity<?> replyMessage(@RequestBody MessageRequestDTO messageRequestDTO) {
        return new Response().success(messageService.replyMessage(messageRequestDTO));
    }

    @GetMapping(value = "/all/by-candidatejob/{id}")
    public ResponseEntity<?> viewAllMessageByCandidateJobId(@PathVariable Long id) {
        return new Response().success(messageService.viewAllMessageByCandidateJobId(id));
    }

}
