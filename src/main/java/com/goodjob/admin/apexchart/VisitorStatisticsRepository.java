package com.goodjob.admin.apexchart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 10.6 방문자 통계 리포지토리 By.OH
 */
public interface VisitorStatisticsRepository extends JpaRepository<VisitorStatistics, LocalDate> {

    List<VisitorStatistics> findAllByXBetween(LocalDate startDate, LocalDate endDate);

    // 현재 날짜가 존재하는지 여부
    boolean existsByX(LocalDate today);

    // 모든 방문 수
    @Query(value = "select SUM(a.y) from VisitorStatistics a", nativeQuery = true)
    Long sumVisitor();
    // 방문 수 증가
    @Modifying
    @Transactional
    @Query("update VisitorStatistics a set a.y=a.y+1 where a.x=:x")
    void updateVisitor(@Param("x") LocalDate x);
}
