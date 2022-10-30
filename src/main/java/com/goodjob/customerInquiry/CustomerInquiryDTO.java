package com.goodjob.customerInquiry;

import lombok.Data;
/**
 * 22.10.30 오성훈
 */
@Data
public class CustomerInquiryDTO {
    private String inquiryPostTitle;
    private String inquiryPostContent;
    private CustomerInquiryPostType inquiryPostCategory;
}
