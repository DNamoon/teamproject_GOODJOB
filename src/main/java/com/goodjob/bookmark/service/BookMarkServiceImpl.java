package com.goodjob.bookmark.service;

import com.goodjob.bookmark.BookMark;
import com.goodjob.bookmark.bookmarkdto.BookMarkDTO;
import com.goodjob.bookmark.repository.BookMarkRepository;
import com.goodjob.member.Member;
import com.goodjob.member.repository.MemberRepository;
import com.goodjob.post.Post;
import com.goodjob.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookMarkServiceImpl implements BookMarkService {

    private final BookMarkRepository bookMarkRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Override
    public Optional<BookMark> saveBookMark(BookMarkDTO bookMarkDTO) {
        BookMark saveBookMark = bookMarkRepository.save(bookMarkDTO.toEntity());
        return Optional.ofNullable(saveBookMark);
    }

    @Override
    public Page<BookMark> findAllByMember(Pageable pageable, Member member) {
       return bookMarkRepository.findAllByBookMarkMemId(pageable,member);
    }

    @Override
    public boolean existsPostByMember(String loginId, Long postId) {
        Member member = memberRepository.findByMemLoginId(loginId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);
        return bookMarkRepository.existsByBookMarkMemIdAndBookMarkPostId(member,post);
    }

    @Override
    public void deleteBookMark(Long bookMarkId) {
        bookMarkRepository.deleteById(bookMarkId);
    }
}
