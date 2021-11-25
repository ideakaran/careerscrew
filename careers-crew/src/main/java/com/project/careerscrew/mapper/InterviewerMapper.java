package com.project.careerscrew.mapper;

import com.project.careerscrew.dto.InterviewerDTO;
import com.project.careerscrew.entities.Interviewer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InterviewerMapper {
    Interviewer toEntity(InterviewerDTO interviewerDTO);

    InterviewerDTO toDto(Interviewer interviewer);

    List<InterviewerDTO> toDto(List<Interviewer> interviewers);

    List<Interviewer> toEntity(List<InterviewerDTO> interviewers);
}
