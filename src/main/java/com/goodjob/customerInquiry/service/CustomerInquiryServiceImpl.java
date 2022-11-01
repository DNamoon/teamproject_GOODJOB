package com.goodjob.customerInquiry.service;

import com.goodjob.customerInquiry.CustomerInquiryPost;
import com.goodjob.customerInquiry.repository.CustomerInquiryPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @Override
    public Optional<CustomerInquiryPost> findOne(Long id) {
        return customerInquiryPostRepository.findById(id);
    }

    @Override
    public Page<CustomerInquiryPost> findInquiryListById(Pageable pageable, HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        String loginId = (String) httpSession.getAttribute("sessionId");
        if (httpSession.getAttribute("Type").equals("member")) {
            return customerInquiryPostRepository.findAllByInquiryPostMemberId(pageable, loginId);
        }
        if (httpSession.getAttribute("Type").equals("company")) {
            return customerInquiryPostRepository.findAllByInquiryPostComId(pageable, loginId);
        }
        return null;
    }

}
