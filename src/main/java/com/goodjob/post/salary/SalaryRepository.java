package com.goodjob.post.salary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepository extends JpaRepository<PostSalary,Long> {
    Optional<PostSalary> findBySalaryRange(String salaryRange);

}
