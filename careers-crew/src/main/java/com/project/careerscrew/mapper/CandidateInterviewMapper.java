package com.project.careerscrew.mapper;

import com.project.careerscrew.dto.CandidateInterviewDTO;
import com.project.careerscrew.entities.CandidateInterview;
import com.project.careerscrew.entities.Interviewer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CandidateInterviewMapper {

    @Mapping(source = "candidateJobId", target = "candidateJob.id")
    @Mapping(source = "interviewersId", target = "interviewers", qualifiedByName = "toInterviewers")
    CandidateInterview toEntity(CandidateInterviewDTO candidateInterviewDTO);

    @Mapping(source = "candidateJob.id", target = "candidateJobId")
    @Mapping(source = "interviewers", target = "interviewersId", qualifiedByName = "toInterviewerId")
    CandidateInterviewDTO toDto(CandidateInterview candidateJob);


    @Named("toInterviewerId")
    static Set<Long> toInterviewerId(Set<Interviewer> interviewers) {
        return interviewers.stream()
                .map(interviewer -> interviewer.getId()).collect(Collectors.toSet());
    }


    @Named("toInterviewers")
    static Set<Interviewer> toInterviewers(Set<Long> interviewersId) {
        Set<Interviewer> interviewersSet = interviewersId.stream()
                .map(interviewerId -> {
                    Interviewer interviewer = new Interviewer();
                    interviewer.setId(interviewerId);
                    return interviewer;
                }).collect(Collectors.toSet());
        return interviewersSet;
    }

}
