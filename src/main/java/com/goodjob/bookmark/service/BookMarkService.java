package com.goodjob.bookmark.service;

import com.goodjob.bookmark.BookMark;
import com.goodjob.bookmark.bookmarkdto.BookMarkDTO;
import com.goodjob.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookMarkService {

    Optional<BookMark> saveBookMark(BookMarkDTO bookMarkDTO);

    Page<BookMark> findAllByMember(Pageable pageable,Member member);

    boolean existsPostByMember(String loginId, Long postId);

    void deleteBookMark(Long bookMarkId);
}
