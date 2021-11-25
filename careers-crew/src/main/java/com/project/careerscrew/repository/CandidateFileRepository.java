package com.project.careerscrew.repository;

import com.project.careerscrew.entities.CandidateFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateFileRepository extends JpaRepository<CandidateFile, Long> {

    Optional<CandidateFile> findById(@Param("id") Long id);

    @Query("SELECT cf FROM CandidateFile cf WHERE cf.candidateJob.userEntity.id = :userId ")
    List<CandidateFile> getAllCandidateFilesByUserId(@Param("userId") Long id);

    @Query("SELECT cf FROM CandidateFile cf WHERE cf.candidateJob.id = :candidateJobId and cf.isPrimaryResume = true")
    Optional<CandidateFile> getCandidateResumeFileByCandidateJobId(@Param("candidateJobId") Long candidateJobId);

}
