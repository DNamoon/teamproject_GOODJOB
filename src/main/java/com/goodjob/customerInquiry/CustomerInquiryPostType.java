package com.goodjob.customerInquiry;
/**
 * 22.10.30 오성훈
 */
public enum CustomerInquiryPostType {

    GENERAL("일반문의"),
    CORRECTION("이메일 및 기타 수정요청"),
    REPORT("허위신고"),
    ETC("기타문의");

    private String customerInquiryPostCategory;

    CustomerInquiryPostType(String customerInquiryPostCategory) {
        this.customerInquiryPostCategory = customerInquiryPostCategory;
    }

    public String getCustomerInquiryPostCategory() {
        return customerInquiryPostCategory;
    }
}
