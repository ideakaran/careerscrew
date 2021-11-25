package com.project.careerscrew.services;

import com.project.careerscrew.dto.JobDTO;
import com.project.careerscrew.entities.Job;
import com.project.careerscrew.mapper.JobMapper;
import com.project.careerscrew.repository.JobRepository;
import com.project.careerscrew.utils.exceptions.AppExceptionConstants;
import com.project.careerscrew.utils.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    @Override
    public JobDTO save(JobDTO jobDTO) {
        return jobMapper.toDto(jobRepository.save(jobMapper.toEntity(jobDTO)));
    }

    /*
    for candidate to view active jobs only
    */
    @Override
    public List<JobDTO> getActiveJobs() {
        return jobMapper.toDto(jobRepository.findAllByIsActiveTrue());

    }

    /*
    for hr to view both active as well as inactive jobs
    */
    @Override
    public List<JobDTO> getAllJobs() {
        List<Job> jobList = jobRepository.findAll();
        List<JobDTO> jobDTOS = jobMapper.toDto(jobList);
        return jobDTOS;
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppExceptionConstants.RECORD_NOT_FOUND));
        return jobMapper.toDto(job);
    }

    @Override
    public JobDTO updateJob(JobDTO jobDTO) {
        Job job = jobRepository.findById(jobDTO.getId()).orElseThrow(() -> new ResourceNotFoundException(AppExceptionConstants.RECORD_NOT_FOUND));
        job.setTitle(jobDTO.getTitle());
        job.setPosition(jobDTO.getPosition());
        job.setDescription(jobDTO.getDescription());
        job.setIsActive(jobDTO.getIsActive());
        jobRepository.save(job);
        return jobMapper.toDto(job);

    }
}
