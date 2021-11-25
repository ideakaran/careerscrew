package com.project.careerscrew.mapper;

import com.project.careerscrew.dto.JobDTO;
import com.project.careerscrew.entities.Job;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface JobMapper {

    Job toEntity(JobDTO jobDTO);

    JobDTO toDto(Job job);

    List<JobDTO> toDto(List<Job> jobs);

    List<Job> toEntity(List<JobDTO> jobs);
}
