package com.goodjob.admin.customerinquiry;


import javax.persistence.Column;
import java.util.Date;

public class CustomerInquiryPost {


    private Long inquiryPostId;

    private String inquiryPostTitle;

    private String inquiryPostContent;

    private String inquiryPostWriter;

    private Date inquiryPostPublishedDate;

    @Column(columnDefinition = "boolean default 0")
    private String inquiryPostStatus;
}
