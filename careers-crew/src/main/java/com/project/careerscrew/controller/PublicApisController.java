package com.project.careerscrew.controller;

import com.project.careerscrew.config.AppProperties;
import com.project.careerscrew.dto.ResumeRequest;
import com.project.careerscrew.services.JobService;
import com.project.careerscrew.services.ResumeService;
import com.project.careerscrew.utils.FieldValidationService;
import com.project.careerscrew.utils.Response;
import com.project.careerscrew.utils.exceptions.AppExceptionConstants;
import com.project.careerscrew.utils.exceptions.CustomAppException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@RestController
@RequestMapping("/public-apis")
@AllArgsConstructor
public class PublicApisController {

    private final FieldValidationService fieldValidationService;
    private final JobService jobService;
    private final ResumeService resumeService;
    private final AppProperties appProperties;


    @GetMapping("/jobs/all")
    public ResponseEntity<?> getJobs() {
        return new Response().success(jobService.getActiveJobs());
    }

    @RequestMapping(value = "/resume/apply", method = RequestMethod.POST)
    public ResponseEntity<?> candidateJobApply(
            @RequestPart("resumeRequest") @Valid ResumeRequest resumeRequest,
            @RequestPart("file") @Valid @NotNull @NotBlank MultipartFile file, BindingResult result) {
        ResponseEntity<?> errorMap = fieldValidationService.validateField(result);
        if (errorMap != null) {
            return errorMap;
        }
        // Resume file size and format validation
        AppProperties.Defaults appPropertiesDefaults = appProperties.getDefaults();
        if (!ObjectUtils.isEmpty(appPropertiesDefaults.getResumeFileSupportedTypes()) || appPropertiesDefaults.getResumeMaxFileSizeMB() > -1) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = "." + fileName.split("\\.")[1];
            boolean isSupportedFormat = Arrays.stream(appPropertiesDefaults.getResumeFileSupportedTypes())
                    .anyMatch(v -> v.equals(fileExtension));
            double BYTES_PER_MB = 0.00000095367432;
            double fileSizeInMB = file.getSize() * BYTES_PER_MB;
            if (!isSupportedFormat || (appPropertiesDefaults.getResumeMaxFileSizeMB() > -1 && fileSizeInMB > appPropertiesDefaults.getResumeMaxFileSizeMB())) {
                throw new CustomAppException(AppExceptionConstants.FILE_FORMAT_NOT_SUPPORTED);
            }
        }
        return new Response().success(resumeService.apply(resumeRequest, file));
    }

    @GetMapping("/referral/verify-candidate")
    public ResponseEntity<?> verifyReferral(@RequestParam("candidateJobId") Long candidateJobId, @RequestParam("referralCode") String code) {
        resumeService.verifyReferral(candidateJobId, code);
        return new Response().success("Verification request successfully processed", Boolean.TRUE);
    }

}
