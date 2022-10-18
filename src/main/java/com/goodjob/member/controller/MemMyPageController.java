package com.goodjob.member.controller;

import com.goodjob.member.memDTO.MemberDTO;
import com.goodjob.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping("/myPage")
    public String myPageForm(HttpSession session, Model model){
        String id = (String)session.getAttribute("sessionId");
        model.addAttribute("memberInfo",memberService.memInfo(id));
        return "member/myPageInfo";
    }

    @PostMapping("/myPageInfo")
    public String infoUpdate(MemberDTO memberDTO){
        memberService.updateMemInfo(memberDTO);
        return "redirect:/member/myPage";
    }

//    @RequestMapping("/delete")
//    public String deleteMember() {
//        // mypage에서 탈퇴 버튼 생성(비밀번호 확인 후 회원 정보 삭제,이력서 정보 같이 삭제)
//        return 썸띵;
//    }
}
