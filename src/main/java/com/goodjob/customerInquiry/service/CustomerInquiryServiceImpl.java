package com.goodjob.customerInquiry.service;

import com.goodjob.customerInquiry.CustomerInquiryPost;
import com.goodjob.customerInquiry.CustomerInquiryPostType;
import com.goodjob.customerInquiry.repository.CustomerInquiryPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 22.10.30 오성훈
 */
@Service
@Transactional
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
    public Page<CustomerInquiryPost> findAll(Pageable pageable) {
        return customerInquiryPostRepository.findAll(pageable);
    }

    @Override
    public void updateInquiryPostWithAnswer(Long id, String inquiryPostAnswer, String inquiryPostAnswerName, LocalDateTime inquiryPostAnswerDate, String status) {
        customerInquiryPostRepository.updateInquiryPostWithAnswer(id, inquiryPostAnswer, inquiryPostAnswerName, inquiryPostAnswerDate, status);
    }

    @Override
    public void deleteByInquiryPostId(Long inquiryPostId) {
        customerInquiryPostRepository.deleteById(inquiryPostId);
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

    @Override
    public Page<CustomerInquiryPost> findAllByInquiryPostStatus(Pageable pageable, String status) {
        return customerInquiryPostRepository.findAllByInquiryPostStatus(pageable, status);
    }

    @Override
    public Page<CustomerInquiryPost> findAllByMemberType(Pageable pageable, String memberType) {
        if (memberType.equals("inquiryPostComId_comId")) {
            return customerInquiryPostRepository.findAllByInquiryPostMemberIdIsNull(pageable);
        }
        if (memberType.equals("inquiryPostMemberId_memId")) {
            return customerInquiryPostRepository.findAllByInquiryPostComIdIsNull(pageable);
        }
        return null;
    }

    @Override
    public Page<CustomerInquiryPost> findAllByCategory(Pageable pageable, String category) {
        CustomerInquiryPostType customerInquiryPostType = CustomerInquiryPostType.valueOf(category);
        return customerInquiryPostRepository.findAllByCategory(pageable, customerInquiryPostType);
    }

    @Override
    public Long countByUnanswered() {
        return customerInquiryPostRepository.countByInquiryPostStatus("0");
    }

    @Override
    public Page<CustomerInquiryPost> findAllByWriter(Pageable pageable, String loginId) {
        return customerInquiryPostRepository.findAllByInquiryPostWriterContaining(pageable, loginId);
    }

    @Override
    public Page<CustomerInquiryPost> findAllByTitle(Pageable pageable, String title) {
        return customerInquiryPostRepository.findByInquiryPostTitleContaining(pageable, title);
    }

    @Override
    public Page<CustomerInquiryPost> findAllByContent(Pageable pageable, String content) {
        return customerInquiryPostRepository.findByInquiryPostContentContaining(pageable,content);
    }

}
