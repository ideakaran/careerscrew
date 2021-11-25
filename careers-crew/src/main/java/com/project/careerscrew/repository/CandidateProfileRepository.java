package com.project.careerscrew.repository;

import com.project.careerscrew.entities.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Long> {
    CandidateProfile getCandidateProfileByCandidateJobId(Long candidateJobId);

}
