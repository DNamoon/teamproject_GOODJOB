package com.goodjob.education.repository;

import com.goodjob.education.MajorName;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * 박채원 22.10.04 작성
 * 박채원 22.11.06 수정 - 검색어로 전공찾는 메소드 수정
 */

public interface MajorNameRepository extends JpaRepository<MajorName, String> {
    List<MajorName> findMajorNameByMajNameContainingAndMajNameNotContaining(String keyword, String noAnswer);
}
