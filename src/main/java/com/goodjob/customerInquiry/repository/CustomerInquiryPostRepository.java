package com.goodjob.customerInquiry.repository;

import com.goodjob.customerInquiry.CustomerInquiryPost;
import com.goodjob.customerInquiry.CustomerInquiryPostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface CustomerInquiryPostRepository extends JpaRepository<CustomerInquiryPost, Long> {
    @Query("select a from CustomerInquiryPost a where a.inquiryPostMemberId.memLoginId=:memLoginId")
    Page<CustomerInquiryPost> findAllByInquiryPostMemberId(Pageable pageable, @Param("memLoginId") String memLoginId);

    @Query("select a from CustomerInquiryPost a where a.inquiryPostComId.comLoginId=:comLoginId")
    Page<CustomerInquiryPost> findAllByInquiryPostComId(Pageable pageable, @Param("comLoginId") String comLoginId);

    @Query("update CustomerInquiryPost a set a.inquiryPostAnswer=:inquiryPostAnswer, a.inquiryPostAnswerName=:inquiryPostAnswerName,a.inquiryPostAnswerDate=:inquiryPostAnswerDate,a.inquiryPostStatus=:status where a.inquiryPostId=:id")
    @Modifying
    void updateInquiryPostWithAnswer(@Param("id") Long id, @Param("inquiryPostAnswer") String inquiryPostAnswer, @Param("inquiryPostAnswerName") String inquiryPostAnswerName, @Param("inquiryPostAnswerDate") LocalDateTime inquiryPostAnswerDate, @Param("status") String status);

    Page<CustomerInquiryPost> findAllByInquiryPostComIdIsNull(Pageable pageable);

    Page<CustomerInquiryPost> findAllByInquiryPostMemberIdIsNull(Pageable pageable);

    @Query("select a from CustomerInquiryPost a where a.inquiryPostCategory=:category")
    Page<CustomerInquiryPost> findAllByCategory(Pageable pageable, @Param("category") CustomerInquiryPostType category);

    Page<CustomerInquiryPost> findAllByInquiryPostStatus(Pageable pageable, @Param("Status") String Status);

    Long countByInquiryPostStatus(@Param("status") String status);

    Page<CustomerInquiryPost> findAllByInquiryPostWriterContaining(Pageable pageable, @Param("loginId") String loginId);
    Page<CustomerInquiryPost> findByInquiryPostTitleContaining(Pageable pageable, @Param("title") String title);
    Page<CustomerInquiryPost> findByInquiryPostContentContaining(Pageable pageable, @Param("content") String content);
}
