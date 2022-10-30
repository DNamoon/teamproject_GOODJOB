package com.goodjob.customerInquiry;

import lombok.Data;

@Data
public class CustomerInquiryDTO {
    private String inquiryPostTitle;
    private String inquiryPostContent;
    private CustomerInquiryPostType inquiryPostCategory;
}
