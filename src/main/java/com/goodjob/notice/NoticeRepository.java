package com.goodjob.notice;

import com.goodjob.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findAllByNoticeIdBetweenOrderByNoticeIdDesc(Long starNum, Long endNum);
}
