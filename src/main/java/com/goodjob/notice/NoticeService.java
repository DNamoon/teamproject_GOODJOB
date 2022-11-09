package com.goodjob.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
/**
 * 22.10.19 By.OH
 */
public interface NoticeService {

    Optional<Notice> findOne(Long noticeId);

    Page<Notice> findNoticeList(Pageable pageable);
    Page<Notice> findNoticeListWithStatus(Pageable pageable,String noticeStatus);
    List<Notice> findAll();

    Notice insertNotice(Notice notice);

    void deleteNotice(Long id);
    void updateNoticeStatus(Long id);
}
