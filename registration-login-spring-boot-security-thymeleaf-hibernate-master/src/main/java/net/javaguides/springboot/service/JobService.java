package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface JobService {
    void saveJob(Job job);
    List<Job> getAllJobs();
    Optional<Job> getJobById(Long id);
    void deleteJob(Long id);
    Page<Job> findAll(Pageable pageable);

    Page<Job> searchJobs(String keyword, Pageable pageable);
}
