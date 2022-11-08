package com.goodjob.customerInquiry;


import com.goodjob.company.Company;
import com.goodjob.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
/**
 * 22.10.30 오성훈
 */
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInquiryPost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long inquiryPostId;

    @Column
    private String inquiryPostTitle;

    @Column(columnDefinition = "text(5000)")
    private String inquiryPostContent;

    @Column
    private String inquiryPostWriter;

    @Column(columnDefinition = "varchar(30)")
    @Enumerated(EnumType.STRING)
    private CustomerInquiryPostType inquiryPostCategory;

    @Column
    @CreationTimestamp
    private LocalDateTime inquiryPostPublishedDate;

    @Column(columnDefinition = "boolean default 0")
    private String inquiryPostStatus;

    @Column
    private String inquiryPostAnswerName;

    @Column(columnDefinition = "text(5000)")
    private String inquiryPostAnswer;

    @Column
    private LocalDateTime inquiryPostAnswerDate;

    @ManyToOne
    private Member inquiryPostMemberId;

    @ManyToOne
    private Company inquiryPostComId;

}
