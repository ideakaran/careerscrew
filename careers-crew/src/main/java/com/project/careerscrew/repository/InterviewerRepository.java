package com.project.careerscrew.repository;

import com.project.careerscrew.entities.Interviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface InterviewerRepository extends JpaRepository<Interviewer, Long> {

    // By Default this is available:  List<Interviewer> findAllById(Iterable<Long> ids);
    // FOr Set<Interview> can use the following method
    @Query("SELECT i FROM Interviewer i WHERE i.id IN (:interviewersId)")
    Set<Interviewer> findSetOfInterviewersHavingId(@Param("interviewersId") Set<Long> interviewersId);


}
