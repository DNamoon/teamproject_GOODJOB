package com.goodjob.bookmark.repository;

import com.goodjob.bookmark.BookMark;
import com.goodjob.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    Page<BookMark> findAllByBookMarkMemId(Pageable pageable, Member member);
}
