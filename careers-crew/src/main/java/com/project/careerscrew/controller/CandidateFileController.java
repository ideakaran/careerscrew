package com.project.careerscrew.controller;

import com.project.careerscrew.dto.CandidateFileDTO;
import com.project.careerscrew.services.CandidateFileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/candidate-file")
@AllArgsConstructor
@Slf4j
public class CandidateFileController {

    private final CandidateFileService candidateFileService;

    @GetMapping("/all/files-by-user-id")
    public ResponseEntity<?> getAllResourceByUserId(@RequestParam("userId") Long userId) {
        log.info("Resource API: getAllResourceByUserId: ", userId);
        List<CandidateFileDTO> allCandidateFilesByUserId = candidateFileService.getAllCandidateFilesByUserId(userId);
        return new ResponseEntity<>(allCandidateFilesByUserId, HttpStatus.OK);
    }

    @GetMapping("/resume/{candidateJobId}")
    public ResponseEntity<?> getCandidateResumeFileByCandidateJobId(@PathVariable("candidateJobId") Long candidateJobId) {
        log.info("Resource API: getCandidateResumeFileByCandidateJobId: ", candidateJobId);
        CandidateFileDTO candidateFileDTO = candidateFileService.getCandidateResumeFileByCandidateJobId(candidateJobId);
        return new ResponseEntity<>(candidateFileDTO, HttpStatus.OK);
    }

    // Any File Upload and Download (CandidateFile)

    @PostMapping("/upload-resource")
    public ResponseEntity<?> uploadResource(@RequestParam("resource") MultipartFile file, @RequestParam("resource") Long candidateJobId) {
        log.info("Resource API: upload resource");
        CandidateFileDTO candidateFileDTO = candidateFileService.storeCandidateFile(file, candidateJobId);
        return new ResponseEntity<>(candidateFileDTO, HttpStatus.OK);
    }

    // Security AccessControl Check (Is resourceOwner or Admin) Done in the downloadCandidateFileById Implementation
    @GetMapping("/download-resources/{candidateFileId}")
    public ResponseEntity<?> downloadResource(HttpServletRequest request, @PathVariable("candidateFileId") Long candidateFileId) {
        log.info("Resource API: download resource from context ", ServletUriComponentsBuilder.fromCurrentContextPath().toString(), candidateFileId);
        // Load file as Resource
        Resource resource = candidateFileService.downloadCandidateFileById(candidateFileId);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.error("Could not determine file type.");
        }
        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
