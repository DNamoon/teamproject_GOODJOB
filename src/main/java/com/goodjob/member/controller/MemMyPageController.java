package com.goodjob.member.controller;

import com.goodjob.member.memDTO.ResumeMemberDTO;
import com.goodjob.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
/**
 * 김도현 22.10.12 작성
 *
 * **/
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemMyPageController {

    private final MemberService memberService;

    @PostMapping("/myPage")
    public String myPageForm(HttpSession session, Model model){
        String id = (String)session.getAttribute("sessionId");
        model.addAttribute("memberInfo",memberService.memInfo(id));
        return "member/myPageInfo";
    }

    @GetMapping("/myPage")
    public String infoUpdate( Long memId, Model model){

        model.addAttribute("resumeId", memId);

        return "/member/myPageInfo";
    }
}
