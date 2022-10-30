package com.goodjob.customerInquiry.service;

import com.goodjob.customerInquiry.CustomerInquiryPost;
import com.goodjob.customerInquiry.repository.CustomerInquiryPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 * 22.10.30 오성훈
 */
@Service
@RequiredArgsConstructor
public class CustomerInquiryServiceImpl implements CustomerInquiryService {

    private final CustomerInquiryPostRepository customerInquiryPostRepository;

    @Override
    public Optional<CustomerInquiryPost> saveInquiry(CustomerInquiryPost customerInquiryPost) {
        CustomerInquiryPost inquiryPost = customerInquiryPostRepository.save(customerInquiryPost);
        return Optional.ofNullable(inquiryPost);
    }
}
