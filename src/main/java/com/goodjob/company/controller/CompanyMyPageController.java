package com.goodjob.company.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/com")
@RequiredArgsConstructor
public class CompanyMyPageController {

    @GetMapping("/myPagePost")
    public String myPageForm(){
        return "/company/companyMyPagePostList";
    }

    @GetMapping("/myPageApplier")
    public String myPageApplierList(){
        return "/company/companyMyPageApplyList";
    }
}
