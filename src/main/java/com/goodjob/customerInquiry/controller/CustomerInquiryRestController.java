package com.goodjob.customerInquiry.controller;

import com.goodjob.customerInquiry.CustomerInquiryPost;
import com.goodjob.customerInquiry.service.CustomerInquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 22.10.31 오성훈
 */
@RestController
@Slf4j
@RequestMapping("/customerInquiry")
@RequiredArgsConstructor
public class CustomerInquiryRestController {

    private final CustomerInquiryService customerInquiryService;

    @GetMapping("/getContent")
    public String loadContent(@Param("idVal") Long idVal){
        CustomerInquiryPost customerInquiryPost = customerInquiryService.findOne(idVal).orElse(null);
        return customerInquiryPost.getInquiryPostContent();
    }
}
