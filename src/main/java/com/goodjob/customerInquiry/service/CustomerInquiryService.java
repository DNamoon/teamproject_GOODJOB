package com.goodjob.customerInquiry.service;

import com.goodjob.customerInquiry.CustomerInquiryPost;

import java.util.Optional;

public interface CustomerInquiryService {

    Optional<CustomerInquiryPost> saveInquiry(CustomerInquiryPost customerInquiryPost);
}
