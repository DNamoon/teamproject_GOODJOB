package com.goodjob.bookmark.contoroller;

import com.goodjob.bookmark.BookMark;
import com.goodjob.bookmark.bookmarkdto.BookMarkDTO;
import com.goodjob.bookmark.service.BookMarkService;
import com.goodjob.member.Member;
import com.goodjob.member.service.MemberService;
import com.goodjob.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/member/bookmark")
@RequiredArgsConstructor
public class BookMarkController {

    private final BookMarkService bookMarkService;
    private final MemberService memberService;
    private final PostService postService;

    @GetMapping
    public String bookMarkHome(Model model, HttpServletRequest request) {
        String loginId = (String) request.getSession(false).getAttribute("sessionId");
        Member member = memberService.loginIdCheck(loginId).orElse(null);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bookMarkId").descending());
        Page<BookMark> bookMarks = bookMarkService.findAllByMember(pageable, member);
        model.addAttribute("bookMarks", bookMarks);
        return "bookmark/bookMarkMain";
    }

    @PostMapping("/save")
    @ResponseBody
    public String saveBookMark(@RequestParam("postId") Long postId, @RequestParam("memberId") String memberId) {
        BookMarkDTO result = BookMarkDTO.builder()
                .bookMarkPostId(postService.findOne(postId).orElse(null))
                .bookMarkMemId(memberService.loginIdCheck(memberId).orElse(null))
                .build();
        bookMarkService.saveBookMark(result);
        return "북마크에 등록되었습니다.";
    }

    @PostMapping("/delete")
    @ResponseBody
    public String deleteBookMark(@RequestParam("bookMarkId") Long bookMarkId) {
        bookMarkService.deleteBookMark(bookMarkId);
        return "삭제되었습니다.";
    }
}
