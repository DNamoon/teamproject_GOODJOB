/**
 * HO - 2022.10.05
 * 기업 회원가입  & 로그인 컨트롤러
 * + 2022.10.06 아이디 중복확인 코드 추가 56~65라인
 *
 * +22.10.26 회원가입 네이밍 변경(register -> signup)
 */
package com.goodjob.company.controller;

import com.goodjob.company.Company;
import com.goodjob.company.dto.CompanyDTO;
import com.goodjob.company.service.CompanyService;
import com.goodjob.member.memDTO.MemberDTO;
import com.goodjob.member.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.PrintWriter;
import java.util.Optional;

@Controller
@Log4j2
@RequestMapping("/com")
public class CompanyController {
//주석삭제
    @Autowired
    private CompanyService companyService;

    //22.10.10추가
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberService memberService;

    @GetMapping("/signup")
    public String companySignUpForm(Model model, HttpServletRequest request, CompanyDTO companyDTO) {

        //22.10.11 세션있으면(로그인 되어있으면) 세션 종료 후 회원 가입 페이지로 넘어가도록 설정.
        logout(request);

        model.addAttribute("companyDTO", companyDTO);
//        model.addAttribute("comdivCode",companyDTO.getComComdivCode());
//        model.addAttribute("comdivName",companyDTO.getComComdivName());
        return "/company/companySignUpForm";
    }

    //회원가입시 돌아가는 로직. 패스워드 일치하지 않으면 회원가입 불가.
    @PostMapping("/signup")
    public String companySignUp(@Valid CompanyDTO companyDTO, BindingResult result, HttpServletResponse response, Model model) throws Exception {
        System.out.println("====================" + companyDTO);
        if(result.hasErrors()){
            model.addAttribute("companyDTO",companyDTO);
            return "/company/companySignUpForm";
        }
        //회원가입시 비밀번호, 비밀번호확인이 동일하지 않을시 회원가입버튼을 눌러도 회원가입이 되지 않도록 하는 코드
        //result.rejectValue의 field는 DTO의 필드, errorCode는 임의로 지정, defaultMessage는 보여줄 메시지
        //defaultMessage는 form_errors.html에서 작성한 ${err}에 나타난다.
        //ho - 22.10.17 getMemPw -> getPw (로그인 폼 input name 통일. DTO 필드 loginId,pw 로 통일)
        if(!companyDTO.getPw().equals(companyDTO.getComPw2())){
            result.rejectValue("comPw2","passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "/company/companySignUpForm";
        }

        //22.10.20 아이디 중복시 에러메시지 + 로그인 폼 반환
        if(companyService.checkId2(companyDTO.getLoginId()) !=0){
            result.rejectValue("loginId","loginIdDuplicated"
            ,"아이디가 중복됩니다. 다른 아이디를 지정하십시오.");
            return "/company/companySignUpForm";
        }

        System.out.println("companyDTO.toString() = " + companyDTO.toString());
        companyService.createCompanyUser(companyDTO);

        //22.10.27 회원가입시 alert 환영메시지 후 메인페이지 이동
        try {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter w = response.getWriter();
            w.write("<script>alert('"+companyDTO.getLoginId()+"님 가입을 환영합니다!');</script>");
//            w.write("<script>swal('회원가입 완료','"+companyDTO.getLoginId()+"님 가입을 환영합니다!','success')" +
//                    ".then(function(){location.href='/';</script>");
            w.write("<script>location.href='/';</script>");
            w.flush();
            w.close();
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        //return "redirect:/";
    }

    //회원가입 아이디 증복확인시 $.ajax 사용하기 위한 코드
    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String memberIdChkPOST(String comLoginId) throws Exception{
        int result = companyService.checkId2(comLoginId);

        if(result != 0) {
            return "fail";	// 중복 아이디가 존재
        } else {
            return "success";	// 중복 아이디 x
        }

    }

    //22.10.10추가
//    @GetMapping("/login")
//    public String loginForm() {
//        return "/company/login";
//    }

    //22.10.10추가
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String login(@ModelAttribute(name = "companyDTO") CompanyDTO companyDTO, HttpServletRequest request) {
        //ho - 22.10.17 getComLoginId -> getLoginId (로그인 폼 input name 통일. DTO 필드 loginId,pw 로 통일) 99,103,113라인 변경
        Optional<Company> com = companyService.loginIdCheck(companyDTO.getLoginId());

        if (com.isPresent()) {  //id가 db에 있으면
            Company company = com.get();
            if (company.getComLoginId().equals(companyDTO.getLoginId())) {  //id 가 있으면
                String encodePw = company.getComPw();
                log.info("DB에 저장된 비밀번호: " + encodePw);
                log.info("비밀번호 입력값 : " + companyDTO.getPw());
                //암호화된 비밀번호와 로그인 시 입력받은 비밀번호 match 확인
                //ho - 22.10.17 getMemPw -> getPw (로그인 폼 input name 통일. DTO 필드 loginId,pw 로 통일) 106,109라인 변경
                if (passwordEncoder.matches(companyDTO.getPw(), encodePw)) {
                    //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
                    HttpSession session = request.getSession();
                    //세션에 로그인 회원 정보 저장
                    session.setAttribute("sessionId", companyDTO.getLoginId());
                    //10.17추가
                    session.setAttribute("Type", "company");

                    return "redirect:/";
                } else {
                    return "redirect:/login?error";  //pw가 틀린 경우
                }
            } else {
                return "/company/companySignUpForm";  //id가 없는 경우 회원가입 폼으로 이동
            }
        } else {
            return "redirect:/login?error"; //id가 없는 경우
        }
    }

    //22.10.10추가
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    //아이디 찾기페이지 이동
    @GetMapping("/findId")
    public String findLoginIdForm(){
        return "/findId";
    }

    //아이디 찾기
    @PostMapping("/findId")
    public String findLoginId(CompanyDTO companyDTO, MemberDTO memberDTO, HttpServletResponse response){

        String findId = companyService.findId2(companyDTO);
        String id = memberService.findId(memberDTO);

        if(findId != "fail") {
            try {
                response.setContentType("text/html; charset=utf-8");
                PrintWriter w = response.getWriter();
                w.write("<script>alert('회원타입은 기업회원입니다! " + companyDTO.getName() + "님의 아이디는 [" + findId + "] 입니다!');</script>");
//            w.write("<script>swal('회원가입 완료','"+companyDTO.getLoginId()+"님 가입을 환영합니다!','success')" +
//                    ".then(function(){location.href='/';</script>");
                w.write("<script>location.href='/login';</script>");
                w.flush();
                w.close();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            if(id != "fail") {
                try {
                    response.setContentType("text/html; charset=utf-8");
                    PrintWriter w = response.getWriter();
                    w.write("<script>alert('회원타입은 개인회원입니다! " + memberDTO.getName() + "님의 아이디는 [" + id + "] 입니다!');</script>");
                    w.write("<script>location.href='/login';</script>");
                    w.flush();
                    w.close();
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            try {
                response.setContentType("text/html; charset=utf-8");
                PrintWriter w = response.getWriter();
                w.write("<script>alert('입력한 회원정보가 없습니다. 다시 확인해주세요.');</script>");
                w.write("<script>location.href='/com/findId';</script>");
                w.flush();
                w.close();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


    }

}
