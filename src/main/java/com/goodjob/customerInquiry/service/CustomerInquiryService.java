package com.goodjob.customerInquiry.service;

import com.goodjob.customerInquiry.CustomerInquiryPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface CustomerInquiryService {

    Optional<CustomerInquiryPost> saveInquiry(CustomerInquiryPost customerInquiryPost);

    Optional<CustomerInquiryPost> findOne(Long id);
    Page<CustomerInquiryPost> findAll(Pageable pageable);

    void deleteByInquiryPostId(Long inquiryPostId);
    Page<CustomerInquiryPost> findInquiryListById(Pageable pageable, HttpServletRequest request);
}
