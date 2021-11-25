package com.project.careerscrew.mapper;

import com.project.careerscrew.dto.JobDTO;
import com.project.careerscrew.dto.ResumeDTO;
import com.project.careerscrew.dto.ResumeRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, CandidateJobMapper.class, CandidateFileMapper.class})
public interface ResumeMapper {
    ResumeMapper INSTANCE = Mappers.getMapper(ResumeMapper.class);

    @Mapping(target = "userEntityDTO", source = "resumeRequest")
    @Mapping(target = "candidateJobDTO", source = "resumeRequest")
    @Mapping(target = "candidateFileDTO", source = "resumeRequest")
    ResumeDTO toDto(ResumeRequest resumeRequest);

    @AfterMapping
    default void afterResumeRequestToResumeDTO(final ResumeRequest resumeRequest, @MappingTarget final ResumeDTO resumeDTO) {
        resumeDTO.getUserEntityDTO().setId(resumeRequest.getUserId());
        resumeDTO.getCandidateJobDTO().setJob(new JobDTO());
        resumeDTO.getCandidateJobDTO().setUserEntity(resumeDTO.getUserEntityDTO());
        resumeDTO.getCandidateJobDTO().getJob().setId(resumeRequest.getJobId());
    }
}
