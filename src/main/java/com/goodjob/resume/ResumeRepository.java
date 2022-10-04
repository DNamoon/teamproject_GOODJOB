package com.goodjob.resume;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 박채원 22.10.02 작성
 */

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
