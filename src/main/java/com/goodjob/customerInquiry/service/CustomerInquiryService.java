package com.goodjob.customerInquiry.service;

import com.goodjob.customerInquiry.CustomerInquiryPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

public interface CustomerInquiryService {

    Optional<CustomerInquiryPost> saveInquiry(CustomerInquiryPost customerInquiryPost);

    Optional<CustomerInquiryPost> findOne(Long id);

    Page<CustomerInquiryPost> findAll(Pageable pageable);

    void updateInquiryPostWithAnswer(Long id, String inquiryPostAnswer, String inquiryPostAnswerName, LocalDateTime inquiryPostAnswerDate, String status);

    void deleteByInquiryPostId(Long inquiryPostId);

    Page<CustomerInquiryPost> findInquiryListById(Pageable pageable, HttpServletRequest request);

    Page<CustomerInquiryPost> findAllByInquiryPostStatus(Pageable pageable, String status);

    Page<CustomerInquiryPost> findAllByMemberType(Pageable pageable, String memberType);

    Page<CustomerInquiryPost> findAllByCategory(Pageable pageable, String memberType);

    Long countByUnanswered();
    Page<CustomerInquiryPost> findAllByWriter(Pageable pageable,String loginId);
    Page<CustomerInquiryPost> findAllByTitle(Pageable pageable,String title);
    Page<CustomerInquiryPost> findAllByContent(Pageable pageable,String content);
}
