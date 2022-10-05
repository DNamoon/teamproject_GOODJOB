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

//    @PostMapping("/com/register")
//    public String comRegister(@Valid CompanyDTO companyDTO, BindingResult result) {
//        if(result.hasErrors()){
//            return "com_register";
//        }
//        System.out.println("companyDTO.toString() = " + companyDTO.toString());
//        companyService.createCompanyUser(companyDTO);
//        return "com_register_alert";
//    }

    @PostMapping("/com/register")
    public String comRegister(CompanyDTO companyDTO) {
        System.out.println("companyDTO.toString() = " + companyDTO.toString());
        companyService.createCompanyUser(companyDTO);
        return "com_register_alert";
    }

    @GetMapping("/com/test")
    public String comRegisterForm2() {
        return "regi_test";
    }

    @PostMapping("/com/test")
    public String comRegister2(CompanyDTO companyDTO) {
        System.out.println(companyDTO.toString());
        Long companyUser = companyService.createCompanyUser(companyDTO);
        log.info(companyUser);
        return "com_register_alert";
    }

    @GetMapping("/test")
    public String com() {
        return "test-form";
    }

    @PostMapping("/test")
    public String createUser(@ModelAttribute(name="companyDTO") CompanyDTO companyDTO) {
        System.out.println("companyDTO= " + companyDTO.toString());

        companyService.createCompanyUser(companyDTO);
        return "com_register_alert";
    }


    @GetMapping("/register")
    public String register() {
        return "register";
    }

}
