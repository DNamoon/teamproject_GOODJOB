package com.goodjob.member.controller;

import com.goodjob.member.Member;
import com.goodjob.member.memDTO.MemberDTO;
import com.goodjob.member.service.MemberService;
import com.goodjob.resume.dto.ResumeListDTO;
import com.goodjob.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import java.util.List;

/**
 * 김도현 22.10.12 작성
 * mypage 개인정보 수정 페이지
 * 박채원 22.10.23 수정 - 이력서관리로 이동하는 메소드 추가
 * **/
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemMyPageController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final ResumeService resumeService;

    @RequestMapping("/myPage")
    public String myPageForm(HttpSession session, Model model){
        String id = (String)session.getAttribute("sessionId");
        model.addAttribute("memberInfo",memberService.memInfo(id));
        return "member/myPageInfo";
    }
   //개인정보 수정 시 비밀번호 확인
    @ResponseBody
    @PostMapping("/checkPW")
    public String checkPW(@Param("pw")String pw, @Param("id")String loginId) {
        Optional<Member> mem = memberService.loginIdCheck(loginId);
        if (mem.isPresent()) {
            Member member = mem.get();
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
    //회원탈퇴 (비밀번호 확인 후 회원 정보 삭제,이력서 정보 같이 삭제)
    @ResponseBody
    @RequestMapping("/delete")
    public String deleteMember(@Param("deletePw")String deletePw, @Param("id")String loginId, @Param("memId")Long memId) {
        Optional<Member> mem = memberService.loginIdCheck(loginId);
        if (mem.isPresent()) {
            Member member = mem.get();
            if (passwordEncoder.matches(deletePw,member.getMemPw())) {
                memberService.deleteById(memId);
                return "0";
            }
        }
        return "1";
    }
    //비밀번호 변경
    @GetMapping("/changePw")
    public String changePwForm(HttpServletRequest session,Model model){
        String id = (String)session.getAttribute("sessionId");
        model.addAttribute("memberInfo",memberService.memInfo(id));
        return "member/changePw";
    }

    @ResponseBody
    @RequestMapping("/changePw")
    public String changePw(@Param("pw2")String pw2, @Param("memId")Long memId) {
        System.out.println("8888"+pw2+"+"+memId);
        memberService.changePassword(pw2,memId);
        return "success";
    }

    @GetMapping("/myPageResume")
    public String myPageResume(){
        return "/member/myPageResumeList";
    }

    //박채원 - restful api 사용해서 리스트 뿌리는 거 해보려고 작성한 메소드
    @ResponseBody
    @GetMapping(value = "/getResumeList/{memId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResumeListDTO>> getResumeList(@PathVariable("memId") Long memId){
        return new ResponseEntity<>(resumeService.getResumeList(memId), HttpStatus.OK);
    }
}
