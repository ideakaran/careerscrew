package com.project.careerscrew.mapper;

import com.project.careerscrew.dto.CandidateRemarkDTO;
import com.project.careerscrew.entities.CandidateRemark;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CandidateRemarkMapper {

    @Mapping(source = "candidateJobId", target = "candidateJob.id")
    CandidateRemark toEntity(CandidateRemarkDTO candidateRemarkDTO);

    @Mapping(source = "candidateJob.id", target = "candidateJobId")
    CandidateRemarkDTO toDto(CandidateRemark candidateRemark);
}
