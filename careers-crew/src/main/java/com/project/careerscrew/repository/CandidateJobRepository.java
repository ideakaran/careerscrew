package com.project.careerscrew.repository;

import com.project.careerscrew.entities.CandidateJob;
import com.project.careerscrew.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateJobRepository extends JpaRepository<CandidateJob, Long> {
    Optional<CandidateJob> findByIdAndReferralCode(Long id, String code);

    @EntityGraph(attributePaths = {"userEntity", "job"}, type = EntityGraph.EntityGraphType.LOAD)
    List<CandidateJob> findAll();

    @Override
    List<CandidateJob> findAllById(Iterable<Long> longs);

    @Query("SELECT cj FROM CandidateJob cj WHERE cj.isEligibleForProbation = true and cj.hasSentProbationCompletionEmail = false " +
            "and cj.probationCompletionDate <= UTC_TIMESTAMP and cj.refererEmail is not null")
    List<CandidateJob> findAllCandidateJobsWithProbationCompletion();


    @EntityGraph(attributePaths = {"userEntity", "job"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT cj FROM CandidateJob cj WHERE cj.userEntity.id = :userId and cj.isArchived = false")
    Optional<CandidateJob> getCandidateJobByUserId(@Param("userId") Long id);

    int countByApplicationStatus(ApplicationStatus applicationStatus);

    int countByIsVerifiedByRefererAndApplicationStatus(Boolean isVerifiedByReferer, ApplicationStatus applicationStatus);

//    int countBy
}
