package com.goodjob.member.controller;

import com.goodjob.member.Member;
import com.goodjob.member.memDTO.MemberDTO;
import com.goodjob.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.http.HttpRequest;

/**
 * 김도현 22.9.29 작성
**/

@Controller
@RequestMapping("/auth/member")
@RequiredArgsConstructor
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/signUp")
    public String signUpForm() {
        return "member/signup";
    }

    @ResponseBody
    @RequestMapping(value="/checkId",method = RequestMethod.GET)
    public Long checkIdDuplication(@RequestParam("id") String id) {
            Long result = memberService.countByMemLoginId(id);
            return result;
    }
    @RequestMapping(value="/signUp",method = RequestMethod.POST)
    public String signUp(@ModelAttribute(name = "memberDTO") MemberDTO memberDTO) {
        memberDTO.setMemPw(passwordEncoder.encode(memberDTO.getMemPw()));
        Member mem =  memberDTO.toEntity();
        memberService.register(mem);
        return "redirect:/";

    }
    @GetMapping("/login")
    public String loginFrom() {
        return "member/login";
    }


    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String login(Member member, MemberDTO memberDTO) {
        if (memberService.loginIdCheck(member.getMemLoginId()).equals(memberDTO.getMemLoginId())) {
            String encodePw = member.getMemPw();
            //암호화된 비밀번호와 로그인 시 입력받은 비밀번호 match 확인
            if (passwordEncoder.matches(memberDTO.getMemPw(), encodePw)) {
                return "redirect:/";
            } else {
                return "member/alert";
            }
        } else{
                return "member/alert";
            }

    }

}
