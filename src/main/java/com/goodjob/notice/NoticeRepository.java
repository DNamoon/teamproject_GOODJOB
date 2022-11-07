package com.goodjob.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 22.10.19 By.OH
 */
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Modifying
    @Query("update Notice a set a.noticeStatus=1 where a.noticeId = :noticeId")
    void updateStatus(@Param("noticeId") Long noticeId);

    Page<Notice> findAllByNoticeStatus(Pageable pageable, @Param("noticeStatus")String noticeStatus);
}
