package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    Page<Job> findByTitleContainingOrLocationContainingOrCompanyContainingOrSalaryRangeContaining(
            String title, String location, String company, String salaryRange, Pageable pageable);
}
