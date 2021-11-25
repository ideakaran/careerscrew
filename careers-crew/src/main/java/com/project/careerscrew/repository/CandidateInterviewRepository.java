package com.project.careerscrew.repository;

import com.project.careerscrew.entities.CandidateInterview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateInterviewRepository extends JpaRepository<CandidateInterview, Long> {
    CandidateInterview getCandidateInterviewByCandidateJobId(Long candidateJobId);
}
