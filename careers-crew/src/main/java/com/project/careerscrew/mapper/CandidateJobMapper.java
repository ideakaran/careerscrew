package com.project.careerscrew.mapper;

import com.project.careerscrew.dto.CandidateJobDTO;
import com.project.careerscrew.entities.CandidateJob;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateJobMapper {
    CandidateJobMapper INSTANCE = Mappers.getMapper(CandidateJobMapper.class);

    @Mapping(source = "appliedDate", target = "appliedDate",dateFormat = "yyyy-MM-dd hh:mm:ss")
//    @Mapping(source = "userId", target = "userEntity.id")
//    @Mapping(source = "jobId", target = "job.id")
    CandidateJob toEntity(CandidateJobDTO candidateJobDTO);

    @Mapping(source = "appliedDate", target = "appliedDate", dateFormat = "yyyy-MM-dd hh:mm:ss")
//    @Mapping(source = "userEntity.id", target = "userId")
//    @Mapping(source = "job.id", target = "jobId")
    CandidateJobDTO toDto(CandidateJob candidateJob);

    @Mapping(source = "appliedDate", target = "appliedDate", dateFormat = "yyyy-MM-dd")
//    @Mapping(source = "userEntity.id", target = "userId")
//    @Mapping(source = "job.id", target = "jobId")
    List<CandidateJobDTO> toDto(List<CandidateJob> jobs);
}
