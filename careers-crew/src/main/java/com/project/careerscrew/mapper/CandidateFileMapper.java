package com.project.careerscrew.mapper;

import com.project.careerscrew.dto.CandidateFileDTO;
import com.project.careerscrew.entities.CandidateFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateFileMapper {

    @Mapping(source = "candidateJobId", target = "candidateJob.id")
    CandidateFile toEntity(CandidateFileDTO candidateFileDTO);

    @Mapping(source = "candidateJob.id", target = "candidateJobId")
    CandidateFileDTO toDto(CandidateFile candidateFile);

    List<CandidateFileDTO> toDto(List<CandidateFile> candidateFile);

}
