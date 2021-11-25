package com.project.careerscrew.mapper;

import com.project.careerscrew.dto.CandidateProfileDTO;
import com.project.careerscrew.entities.CandidateProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CandidateProfileMapper {

    @Mapping(source = "candidateJobId", target = "candidateJob.id")
    CandidateProfile toEntity(CandidateProfileDTO candidateProfileDTO);

    @Mapping(source = "candidateJob.id", target = "candidateJobId")
    CandidateProfileDTO toDto(CandidateProfile candidateProfile);
}
