package com.project.careerscrew.services;

import com.project.careerscrew.dto.CandidateFileDTO;
import com.project.careerscrew.entities.CandidateFile;
import com.project.careerscrew.entities.CandidateJob;
import com.project.careerscrew.entities.UserEntity;
import com.project.careerscrew.mapper.CandidateFileMapper;
import com.project.careerscrew.repository.CandidateFileRepository;
import com.project.careerscrew.repository.CandidateJobRepository;
import com.project.careerscrew.security.ExtractAuthUser;
import com.project.careerscrew.utils.FileUploadUtils;
import com.project.careerscrew.utils.exceptions.AppExceptionConstants;
import com.project.careerscrew.utils.exceptions.CustomAppException;
import com.project.careerscrew.utils.exceptions.ForbiddenAccessException;
import com.project.careerscrew.utils.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CandidateFileServiceImpl implements CandidateFileService {

    private final CandidateFileRepository candidateFileRepository;
    private final CandidateJobRepository candidateJobRepository;
    private final CandidateFileMapper candidateFileMapper;

    @Override
    public CandidateFileDTO getCandidateFileById(Long id) {
        CandidateFile candidateFile = candidateFileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppExceptionConstants.REQUESTED_RESOURCE_NOT_FOUND));
        checkAccessForFileService(candidateFile.getCandidateJob().getUserEntity().getId());
        return candidateFileMapper.toDto(candidateFile);
    }

    @Override
    public List<CandidateFileDTO> getAllCandidateFilesByUserId(Long userId) {
        // If ROLE_USER current principle id must be equal to candidateJob->userEntity->id
        boolean hasAdminAccessOrIsOwner = ExtractAuthUser.hasAdminAccessOrMatchesUserId(userId);
        if (!hasAdminAccessOrIsOwner) {
            throw new ForbiddenAccessException(AppExceptionConstants.FORBIDDEN_ACCESS);
        }
        checkAccessForFileService(userId);
        List<CandidateFile> allCandidateFilesByUserId = candidateFileRepository.getAllCandidateFilesByUserId(userId);
        return candidateFileMapper.toDto(allCandidateFilesByUserId);
    }

    @Override
    public CandidateFileDTO getCandidateResumeFileByCandidateJobId(Long candidateJobId) {
        CandidateFile candidateFile = candidateFileRepository.getCandidateResumeFileByCandidateJobId(candidateJobId)
                .orElseThrow(() -> new ResourceNotFoundException(AppExceptionConstants.REQUESTED_RESOURCE_NOT_FOUND));
        checkAccessForFileService(candidateFile.getCandidateJob().getUserEntity().getId());
        return candidateFileMapper.toDto(candidateFile);
    }

    @Override
    public CandidateFileDTO storeCandidateFile(MultipartFile file, Long candidateJobId) {

        CandidateJob candidateJob = candidateJobRepository.findById(candidateJobId)
                .orElseThrow(() -> new ResourceNotFoundException(AppExceptionConstants.REQUESTED_RESOURCE_NOT_FOUND));
        checkAccessForFileService(candidateJob.getUserEntity().getId());
        String fileNameForMultipartFile = FileUploadUtils.createCleanFileNameForMultipartFile(file);
        FileUploadUtils.uploadFile(file, fileNameForMultipartFile);
        CandidateFileDTO candidateFileDTO = new CandidateFileDTO();
        candidateFileDTO.setIsPrimaryResume(false);
        candidateFileDTO.setPath(fileNameForMultipartFile);
        candidateFileDTO.setFileName(fileNameForMultipartFile);

        CandidateFile candidateFile = candidateFileMapper.toEntity(candidateFileDTO);
        candidateFile.setCandidateJob(candidateJob);
        candidateFileRepository.save(candidateFile);

        return candidateFileMapper.toDto(candidateFile);
    }

    @Override
    public Resource downloadCandidateFileById(Long id) {
        try {
            CandidateFile candidateFile = candidateFileRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(AppExceptionConstants.REQUESTED_CANDIDATE_FILE_RECORD_NOT_FOUND));
            UserEntity userEntity = candidateFile.getCandidateJob().getUserEntity();
            Long userId = userEntity.getId();
            if (!ExtractAuthUser.hasAdminAccessOrMatchesUserId(userId)) {
                throw new ForbiddenAccessException(AppExceptionConstants.FORBIDDEN_ACCESS);
            }
            Path filePath = FileUploadUtils.getFileUploadDirectory().resolve(candidateFile.getPath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new CustomAppException(AppExceptionConstants.DOWNLOADABLE_RESOURCE_NOT_FOUND);
            }
            return resource;
        } catch (MalformedURLException e) {
            log.error("File download MalformedURLException: {}", e.getMessage());
            throw new CustomAppException(AppExceptionConstants.DOWNLOADABLE_RESOURCE_NOT_FOUND);
        }
    }

    private void checkAccessForFileService(Long candidateJobUserId) {
        // If ROLE_USER current principle id must be equal to candidateJob->userEntity->id
        boolean hasAdminAccessOrIsOwner = ExtractAuthUser.hasAdminAccessOrMatchesUserId(candidateJobUserId);
        if (!hasAdminAccessOrIsOwner) {
            throw new ForbiddenAccessException(AppExceptionConstants.FORBIDDEN_ACCESS);
        }
    }
}
