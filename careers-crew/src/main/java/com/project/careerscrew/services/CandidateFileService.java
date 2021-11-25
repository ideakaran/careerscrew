package com.project.careerscrew.services;

import com.project.careerscrew.dto.CandidateFileDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateFileService {

    CandidateFileDTO getCandidateFileById(Long id);

    List<CandidateFileDTO> getAllCandidateFilesByUserId(Long userId);

    CandidateFileDTO getCandidateResumeFileByCandidateJobId(Long candidateJobId);

    // Store and Download MultipartFile

    CandidateFileDTO storeCandidateFile(MultipartFile file, Long candidateJobId);

    Resource downloadCandidateFileById(Long candidateFileId);

}
