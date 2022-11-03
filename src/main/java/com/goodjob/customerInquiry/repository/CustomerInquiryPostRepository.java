package com.goodjob.customerInquiry.repository;

import com.goodjob.admin.customerInquiry.CustomerInquiryPostAnswerDTO;
import com.goodjob.company.Company;
import com.goodjob.customerInquiry.CustomerInquiryPost;
import com.goodjob.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.sql.Date;

public interface CustomerInquiryPostRepository extends JpaRepository<CustomerInquiryPost, Long> {
    @Query("select a from CustomerInquiryPost a where a.inquiryPostMemberId.memLoginId=:memLoginId")
    Page<CustomerInquiryPost> findAllByInquiryPostMemberId(Pageable pageable, @Param("memLoginId") String memLoginId);

    @Query("select a from CustomerInquiryPost a where a.inquiryPostComId.comLoginId=:comLoginId")
    Page<CustomerInquiryPost> findAllByInquiryPostComId(Pageable pageable, @Param("comLoginId") String comLoginId);

    @Query("update CustomerInquiryPost a set a.inquiryPostAnswer=:inquiryPostAnswer, a.inquiryPostAnswerName=:inquiryPostAnswerName, a.inquiryPostAnswerDate=:inquiryPostAnswerDate where a.inquiryPostId=:id")
    @Modifying
    void updateInquiryPostWithAnswer(@Param("id") Long id, @Param("inquiryPostAnswer") String inquiryPostAnswer, @Param("inquiryPostAnswerName") String inquiryPostAnswerName,@Param("inquiryPostAnswerDate") Date inquiryPostAnswerDate);
}
