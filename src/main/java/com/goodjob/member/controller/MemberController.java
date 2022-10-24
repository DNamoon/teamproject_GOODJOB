package com.goodjob.member.controller;

import com.goodjob.member.Member;
import com.goodjob.member.memDTO.MailDTO;
import com.goodjob.member.memDTO.MemberDTO;
import com.goodjob.member.service.MailService;
import com.goodjob.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

/**
 * 김도현 22.9.29 작성
 * 김도현 22.10.07 수정 (login 메소드 구조 변경)
 * 김도현 22.10.20 수정 (이메일확인, 임시비번 메일 발송 메소드 추가)
 **/

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @GetMapping("/signUp")
    public String signUpForm(HttpServletRequest request, Model model, MemberDTO memberDTO) {
        // 회원가입 시 기존 로그인 상태면 로그아웃 실행
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        model.addAttribute("signUpCheck", memberDTO);
        return "member/signup";
    }

    //ID 중복확인
    @ResponseBody
    @RequestMapping(value="/checkId",method = RequestMethod.GET)
    public Long checkIdDuplication(@RequestParam("id") String id) {
        Long result = memberService.countByMemLoginId(id);
        return result;
    }

    //회원가입
    @RequestMapping(value="/signUp",method = RequestMethod.POST)
    public String signUp(@Valid @ModelAttribute(name = "memberDTO") MemberDTO memberDTO , BindingResult result) {
        //ho - 22.10.17 getMemPw -> getPw (로그인 폼 input name 통일. DTO 필드 loginId,pw 로 통일)
        if(result.hasErrors()){
            return "member/signup";
        }
        memberDTO.setPw(passwordEncoder.encode(memberDTO.getPw()));
        Member mem = memberDTO.toEntity();
        memberService.register(mem);
        return "redirect:/";

    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute(name = "memberDTO") MemberDTO memberDTO, HttpServletRequest request) {
        //ho - 22.10.17 getMemLoginId -> getLoginId (로그인 폼 input name 통일. DTO 필드 loginId,pw 로 통일)
        Optional<Member> mem = memberService.loginIdCheck(memberDTO.getLoginId());

        if (mem.isPresent()) {  // id null 체크
            Member member = mem.get();
            if (member.getMemLoginId().equals(memberDTO.getLoginId())) {  //id 가 있으면
                String encodePw = member.getMemPw();
                //암호화된 비밀번호와 로그인 시 입력받은 비밀번호 match 확인
                if (passwordEncoder.matches(memberDTO.getPw(), encodePw)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("sessionId", memberDTO.getLoginId());
                    session.setAttribute("Type", "member");
                    return "redirect:/"; // 로그인 성공 시 메인페이지
                } else {
                    return "redirect:/login?error";  //pw가 틀린 경우
                }
            } else {
                return "redirect:/login?error"; //id가 틀린경우
            }
        } else {
            return "redirect:/login?error";  //id가 없는 경우
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
    @GetMapping("/checkEmail")
    public String checkEmailForm(){
        return "member/findPw";
    }
    //이메일이 DB에 존재하는지 확인
    @ResponseBody
    @PostMapping("/checkEmail")
    public boolean checkEmail(@RequestParam("memberEmail") String memEmail) {
        return memberService.checkEmail(memEmail);
    }
    //임시비밀번호 발급
    @PostMapping("/sendPw")
    public String sendPwdEmail(@RequestParam("memberEmail") String memberEmail) {

        /** 임시 비밀번호 생성 **/
        String tmpPw = memberService.getTmpPassword();

        /** 임시 비밀번호 저장 **/
        memberService.updatePassword(tmpPw, memberEmail);

        /** 메일 생성 & 전송 **/
        mailService.sendMail(memberEmail,tmpPw);

        return "login";
    }

}