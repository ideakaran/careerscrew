package com.project.careerscrew.repository;

import com.project.careerscrew.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findAllByIsActiveTrue();
    int countByIsActive(Boolean isActive);
}
