package com.goodjob.company.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/com")
@RequiredArgsConstructor
public class CompanyMyPageController {

    @GetMapping("/myPageApplier/{postId}")
    public String myPageApplierList(@PathVariable("postId") Long postId, Model model){
        model.addAttribute("postId", postId);
        return "company/companyMyPageApplyList";
    }
}
