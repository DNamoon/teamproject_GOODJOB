package com.goodjob.education.repository;

import com.goodjob.education.SchoolName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 박채원 22.10.03 작성
 */

public interface SchoolNameRepository extends JpaRepository<SchoolName, String> {

    @Query("select s from SchoolName s where s.schName like %:keyword%")
    List<SchoolName> findSchoolName(String keyword);
}
