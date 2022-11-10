package com.goodjob.bookmark.repository;

import com.goodjob.bookmark.BookMark;
import com.goodjob.member.Member;
import com.goodjob.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    Page<BookMark> findAllByBookMarkMemId(Pageable pageable, Member member);

    boolean existsByBookMarkMemIdAndBookMarkPostId(Member member, Post post);
}
