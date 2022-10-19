package com.goodjob.member.controller;

import com.goodjob.member.Member;
import com.goodjob.member.memDTO.MemberDTO;
import com.goodjob.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * 김도현 22.10.12 작성
 *
 * **/
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemMyPageController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping("/myPage")
    public String myPageForm(HttpSession session, Model model){
        String id = (String)session.getAttribute("sessionId");
        model.addAttribute("memberInfo",memberService.memInfo(id));
        return "member/myPageInfo";
    }
   //개인정보 수정 시 비밀번호 확인
    @ResponseBody
    @PostMapping("/checkPW")
    public String checkPW(@Param("pw")String pw, @Param("id")String id) {
        Optional<Member> mem = memberService.loginIdCheck(id);
        if (mem.isPresent()) {  // id null 체크
            Member member = mem.get();
            System.out.println("++"+member.getMemPw());
            if (passwordEncoder.matches(pw,member.getMemPw())) {
                return "0";
            }
        }
        return "1";
    }
    @PostMapping("/myPageInfo")
    public String infoUpdate(MemberDTO memberDTO){
        memberService.updateMemInfo(memberDTO);
        return "redirect:/member/myPage";
    }

//    @RequestMapping("/delete")
//    public String deleteMember() {
//        // mypage에서 탈퇴 버튼 생성(비밀번호 확인 후 회원 정보 삭제,이력서 정보 같이 삭제)
//        return "home";
//    }
}
