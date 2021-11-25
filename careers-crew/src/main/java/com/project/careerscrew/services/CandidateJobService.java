package com.project.careerscrew.services;

import com.project.careerscrew.dto.CandidateJobDTO;
import com.project.careerscrew.entities.CandidateJob;

import java.util.List;

public interface CandidateJobService {

    // CRUD
    List<CandidateJob> getAllCandidateJobs();

    CandidateJob getCandidateJobById(Long id);

    CandidateJobDTO getCandidateCurrentJobByUserId(Long userId);

    void sendAssignment(CandidateJobDTO candidateJobDTO);

    void submitResponse(CandidateJobDTO candidateJobDTO);

    CandidateJob rejectApplication(Long candidateJobId);

}
