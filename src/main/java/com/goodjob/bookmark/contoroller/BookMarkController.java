package com.goodjob.bookmark.contoroller;

import com.goodjob.bookmark.BookMark;
import com.goodjob.bookmark.service.BookMarkService;
import com.goodjob.member.Member;
import com.goodjob.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/member/bookmark")
@RequiredArgsConstructor
public class BookMarkController {

    private final BookMarkService bookMarkService;
    private final MemberService memberService;
    @GetMapping
    public String bookMarkHome(Model model, HttpServletRequest request){
        String loginId = (String) request.getSession(false).getAttribute("sessionId");
        Member member = memberService.loginIdCheck(loginId).orElse(null);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bookMarkId").descending());
        Page<BookMark> bookMarks = bookMarkService.findAllByMember(pageable, member);
        model.addAttribute("bookMarks",bookMarks);
        return "bookmark/bookMarkMain";
    }
}
