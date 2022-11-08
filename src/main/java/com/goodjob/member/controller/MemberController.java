package com.goodjob.member.controller;

import com.goodjob.member.Member;
import com.goodjob.member.memDTO.MemberDTO;
import com.goodjob.member.service.MailService;
import com.goodjob.member.service.MemberService;
import com.goodjob.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final StatusService statusService;

    @GetMapping("/signUp")
    public String signUpForm(HttpServletRequest request, Model model, MemberDTO memberDTO) {
        // 회원가입 시 기존 로그인 상태면 로그아웃 실행
        logout(request);
        model.addAttribute("signUpCheck", memberDTO);
        return "member/signup";
    }

    //ID 중복확인
    @ResponseBody
    @RequestMapping(value="/checkId",method = RequestMethod.POST)
    public int checkIdDuplication(@RequestParam("id") String id) {
        int result = memberService.checkId2(id);
            if(result != 0) {
                return 1;	// 중복 아이디가 존재
            } else {
                return 2;	// 중복 아이디 x
            }
    }

    //email 중복확인
    @ResponseBody
    @RequestMapping(value="/signupEmail",method = RequestMethod.GET)
    public String checkEmailDuplication(@RequestParam("email") String email) {
        String result = memberService.checkEmail(email);
        return result;

    }

    //회원가입
    @RequestMapping(value="/signUp",method = RequestMethod.POST)
    public String signUp(@Valid @ModelAttribute(name = "memberDTO") MemberDTO memberDTO , BindingResult result, RedirectAttributes redirectAttributes) {

        if(result.hasErrors()){
            return "member/signup";
            }
        // 아이디 중복 시 에러 발생
        if(memberService.checkId2(memberDTO.getLoginId()) !=0){
            result.rejectValue("","loginIdDuplicated"
                    ,"아이디가 중복됩니다. 다른 아이디를 지정하십시오.");
            return "member/signup";
        }
        //비밀번호 일치하지 않을 시 에러 발생
        if(!memberDTO.getPw().equals(memberDTO.getPw2())){
            result.rejectValue("","passwordInCorrect",
                    "입력하신 비밀번호가 일치하지 않습니다.");
            return "member/signup";
        }
        //이메일 중복 시 에러 발생
        if(memberService.checkEmail(memberDTO.getMemEmail1()+"@"+memberDTO.getMemEmail2()) != "false"){
            result.rejectValue("","emailInCorrect",
                    "이미 가입된 이메일입니다.");
            return "member/signup";
        }
        memberDTO.setPw(passwordEncoder.encode(memberDTO.getPw()));
        Member mem = memberDTO.toEntity();
        memberService.register(mem);
        redirectAttributes.addAttribute("param", "1");
        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute(name = "memberDTO") MemberDTO memberDTO, HttpServletRequest request, RedirectAttributes redirectAttributes) {
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

                    //박채원 22.11.02 추가 (이하 5줄) - 합격한 회사가 있는지 확인
                    String loginId = (String) session.getAttribute("sessionId");
                    if(statusService.havePass(loginId)){
                        redirectAttributes.addAttribute("havePass",String.valueOf(statusService.havePass(loginId)));
                    }
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
    public String checkEmail(@RequestParam("memEmail") String memEmail) {
        return memberService.checkEmail(memEmail);
    }

    @PostMapping("/sendPw")
    public String sendPwdEmail(@RequestParam("memberEmail") String memberEmail,@RequestParam("mailType")String mailType) {
       // 임시 비밀번호 생성
        String tmpPw = memberService.getTmpPassword();
        // 임시 비밀번호 저장
        memberService.updatePassword(tmpPw, memberEmail,mailType);
        // 메일 생성 & 전송
        mailService.sendMail(memberEmail,tmpPw);
        return "login";
    }

}