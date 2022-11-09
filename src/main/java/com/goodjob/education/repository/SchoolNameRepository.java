package com.goodjob.education.repository;

import com.goodjob.education.SchoolName;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * 박채원 22.10.03 작성
 * 박채원 22.11.06 수정
 */

public interface SchoolNameRepository extends JpaRepository<SchoolName, String> {
    List<SchoolName> findSchoolNameBySchNameContainingAndSchNameNotContaining(String keyword, String noAnswer);
}
