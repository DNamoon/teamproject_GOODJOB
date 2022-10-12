/**
 * HO - 2022.10.05
 * 기업 회원가입  & 로그인 컨트롤러
 * + 2022.10.06 아이디 중복확인 코드 추가 56~65라인
 */
package com.goodjob.company;

import com.goodjob.company.dto.CompanyDTO;
import com.goodjob.company.service.CompanyService;
import com.goodjob.member.Member;
import com.goodjob.member.memDTO.MemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

    //@GetMapping("/login")
    public String loginMember() {
        return "/company/comLoginForm";
    }

    @GetMapping("/register")
    public String comRegisterForm(Model model, HttpServletRequest request) {
        //22.10.11 세션있으면(로그인 되어있으면) 세션 종료 후 회원 가입 페이지로 넘어가도록 설정.
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        model.addAttribute("companyDTO", new CompanyDTO());
        return "/company/comRegisterForm";
    }

    //회원가입시 돌아가는 로직. 패스워드 일치하지 않으면 회원가입 불가.
    @PostMapping("/register")
    public String comRegister(@Valid CompanyDTO companyDTO, BindingResult result) {
        if(result.hasErrors()){
            return "/company/comRegisterForm";
        }
        //회원가입시 비밀번호, 비밀번호확인이 동일하지 않을시 회원가입버튼을 눌러도 회원가입이 되지 않도록 하는 코드
        //result.rejectValue의 field는 DTO의 필드, errorCode는 임의로 지정, defaultMessage는 보여줄 메시지
        //defaultMessage는 form_errors.html에서 작성한 ${err}에 나타난다.
        if(!companyDTO.getComPw1().equals(companyDTO.getComPw2())){
            result.rejectValue("comPw2","passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "/company/comRegisterForm";
        }
        System.out.println("companyDTO.toString() = " + companyDTO.toString());
        companyService.createCompanyUser(companyDTO);
        return "/company/comRegisterView";
    }

    //회원가입 아이디 증복확인시 $.ajax 사용하기 위한 코드
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public String memberIdChkPOST(String comLoginId) throws Exception{
        int result = companyService.checkId2(comLoginId);

        if(result != 0) {
            return "fail";	// 중복 아이디가 존재
        } else {
            return "success";	// 중복 아이디 x
        }

    }

    //22.10.10추가
    @GetMapping("/login")
    public String loginFrom() {
        return "/company/login";
    }

    //22.10.10추가
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String login(@ModelAttribute(name = "companyDTO") CompanyDTO companyDTO, HttpServletRequest request) {
        Optional<Company> com = companyService.loginIdCheck(companyDTO.getComLoginId());

        if (com.isPresent()) {  //id가 db에 있으면
            Company company = com.get();
            if (company.getComLoginId().equals(companyDTO.getComLoginId())) {  //id 가 있으면
                String encodePw = company.getComPw();
                //암호화된 비밀번호와 로그인 시 입력받은 비밀번호 match 확인
                if (passwordEncoder.matches(companyDTO.getComPw1(), encodePw)) {
                    //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
                    HttpSession session = request.getSession();
                    //세션에 로그인 회원 정보 저장
                    session.setAttribute("sessionId", companyDTO.getComLoginId());
                    session.setAttribute("Type", "company");

                    return "redirect:/";
                } else {
                    return "redirect:login?error";  //pw가 틀린 경우
                }
            } else {
                return "member/signup";
            }
        } else {
            return "redirect:login?error"; //id가 없는 경우
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

}