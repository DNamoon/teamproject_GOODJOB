package com.goodjob.customerInquiry.controller;


import com.goodjob.company.service.CompanyService;
import com.goodjob.customerInquiry.CustomerInquiryDTO;
import com.goodjob.customerInquiry.CustomerInquiryPost;
import com.goodjob.customerInquiry.service.CustomerInquiryService;
import com.goodjob.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * 22.10.30 오성훈 고객문의 컨트롤러 URI는 고민 후 간결하게 변경할 예정
 */
@Controller
@Slf4j
@RequestMapping("/customerInquiry")
@RequiredArgsConstructor
public class CustomerInquiryController {

    private final CustomerInquiryService customerInquiryService;
    private final CompanyService companyService;
    private final MemberService memberService;

    @GetMapping
    public String customerInquiryMain() {
        return "/customerInquiry/customerInquiryMain";
    }

    @GetMapping("/faq")
    public String customerInquiryFAQ() {
        return "/customerInquiry/customerInquiryFAQ";
    }

    @GetMapping("/qna")
    public String customerInquiryQNA(Model model, HttpServletRequest request) {
        if (request.getSession(false).getAttribute("Type") == null) {
            log.info("request.getSession(false) is null");

            return "/customerInquiry/customerInquiryQNA";
        }
        if (request.getSession(false) != null) {
            Sort sort = Sort.by("inquiryPostId").descending();
            Pageable pageable = PageRequest.of(0, 5, sort);
            model.addAttribute("inquiryPostList", customerInquiryService.findInquiryListById(pageable, request));
            return "/customerInquiry/customerInquiryQNA";
        }
        return "/customerInquiry/customerInquiryQNA";
    }

    @PostMapping("/qna")
    public String saveInquiryPost(@ModelAttribute CustomerInquiryDTO customerInquiryDTO, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("Type").equals("company")) {
            CustomerInquiryPost inquiryPostByCompany = CustomerInquiryPost.builder()
                    .inquiryPostComId(companyService.loginIdCheck((String) session.getAttribute("sessionId")).orElse(null))
                    .inquiryPostCategory(customerInquiryDTO.getInquiryPostCategory())
                    .inquiryPostContent(customerInquiryDTO.getInquiryPostContent())
                    .inquiryPostTitle(customerInquiryDTO.getInquiryPostTitle())
                    .inquiryPostWriter((String) session.getAttribute("sessionId"))
                    .inquiryPostPublishedDate(LocalDateTime.now())
                    .inquiryPostStatus("0")
                    .build();
            customerInquiryService.saveInquiry(inquiryPostByCompany);
            return "redirect:/customerInquiry/qna";
        }
        if (session.getAttribute("Type").equals("member")) {
            CustomerInquiryPost inquiryPostByMember = CustomerInquiryPost.builder()
                    .inquiryPostMemberId(memberService.loginIdCheck((String) session.getAttribute("sessionId")).orElse(null))
                    .inquiryPostCategory(customerInquiryDTO.getInquiryPostCategory())
                    .inquiryPostContent(customerInquiryDTO.getInquiryPostContent())
                    .inquiryPostTitle(customerInquiryDTO.getInquiryPostTitle())
                    .inquiryPostWriter((String) session.getAttribute("sessionId"))
                    .inquiryPostPublishedDate(LocalDateTime.now())
                    .inquiryPostStatus("0")
                    .build();
            customerInquiryService.saveInquiry(inquiryPostByMember);
            return "redirect:/customerInquiry/qna";
        }
        return "redirect:/customerInquiry/qna";
    }

    @GetMapping("/policy")
    public String customerInquiryPolicy() {
        return "/customerInquiry/customerInquiryPolicy";
    }
}
