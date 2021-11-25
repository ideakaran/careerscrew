package com.project.careerscrew.services;

import com.project.careerscrew.dto.JobDTO;

import java.util.List;

public interface JobService{
    JobDTO save(JobDTO jobDTO);
    List<JobDTO> getActiveJobs();
    List<JobDTO> getAllJobs();
    JobDTO getJobById(Long id);
    JobDTO updateJob(JobDTO jobDTO);
}
