package com.goodjob.education.repository;

import com.goodjob.education.MajorName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 박채원 22.10.04 작성
 */

public interface MajorNameRepository extends JpaRepository<MajorName, String> {

    @Query("select m from MajorName m where m.majName like %:keyword%")
    List<MajorName> findMajorName(String keyword);
}
