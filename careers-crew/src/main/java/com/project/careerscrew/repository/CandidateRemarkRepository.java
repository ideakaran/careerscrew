package com.project.careerscrew.repository;

import com.project.careerscrew.entities.CandidateRemark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRemarkRepository extends JpaRepository<CandidateRemark, Long> {
    CandidateRemark getCandidateRemarkByCandidateJobId(Long candidateJobId);
}
