/**
 * HO - 2022.10.05
 * 기업 회원가입  & 로그인 컨트롤러
 */
package com.goodjob.company;

import com.goodjob.company.dto.CompanyDTO;
import com.goodjob.company.service.CompanyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Log4j2
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/loginMember")
    public String loginMember() {
        return "loginMember";
    }

    @GetMapping("/com/register")
    public String comRegisterForm(Model model) {
        model.addAttribute("companyDTO", new CompanyDTO());
        return "com_register";
    }

    @PostMapping("/com/register")
    public String comRegister(@Valid CompanyDTO companyDTO, BindingResult result) {
        if(result.hasErrors()){
            return "com_register";
        }
        //회원가입시 비밀번호, 비밀번호확인이 동일하지 않을시 회원가입버튼을 눌러도 회원가입이 되지 않도록 하는 코드
        //result.rejectValue의 field는 DTO의 필드, errorCode는 임의로 지정, defaultMessage는 보여줄 메시지
        if(!companyDTO.getComPw1().equals(companyDTO.getComPw2())){
            result.rejectValue("comPw2","passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "com_register";
        }
        System.out.println("companyDTO.toString() = " + companyDTO.toString());
        companyService.createCompanyUser(companyDTO);
        return "com_register_alert";
    }

//    @PostMapping("/com/register")
//    public String comRegister(CompanyDTO companyDTO) {
//        System.out.println("companyDTO.toString() = " + companyDTO.toString());
//        companyService.createCompanyUser(companyDTO);
//        return "com_register_alert";
//    }
}
