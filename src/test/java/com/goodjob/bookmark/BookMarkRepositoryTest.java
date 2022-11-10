package com.goodjob.bookmark;

import com.goodjob.bookmark.bookmarkdto.BookMarkDTO;
import com.goodjob.bookmark.repository.BookMarkRepository;
import com.goodjob.bookmark.service.BookMarkService;
import com.goodjob.member.Member;
import com.goodjob.member.repository.MemberRepository;
import com.goodjob.post.Post;
import com.goodjob.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.LongStream;

@SpringBootTest
@Transactional
class BookMarkRepositoryTest {
    @Autowired
    BookMarkService bookMarkService;
    @Autowired
    BookMarkRepository markRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    void 북마크조회() {
        Member test = memberRepository.findLoginInfo("test");
        Pageable bookMarkId = PageRequest.of(0, 10, Sort.by("bookMarkId").descending());
        Page<BookMark> allByMember = bookMarkService.findAllByMember(bookMarkId, test);
        for (BookMark bookMark : allByMember) {
            System.out.println("bookMark = " + bookMark.getBookMarkId());
        }
    }
    @Test
    void 북마크중복조회(){
        // case 1
        Member test = memberRepository.findLoginInfo("test");
        List<BookMark> memberBookMark = test.getMemberBookMark();
        for (BookMark bookMark : memberBookMark) {
            System.out.println("bookMark = " + bookMark.getBookMarkId());
        }
        // case 2
        Post post = postRepository.findById(135L).orElse(null);
        boolean b = markRepository.existsByBookMarkMemIdAndBookMarkPostId(test, post);
        System.out.println("b = " + b);
    }
}