package com.goodjob.post.postdto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CompanyInfoDTO {
    private String postcode;
    private String address_name;
    private String detail_name;
    private String etc;

    private String comName;
    private String com_business_number;
    private String com_div;

}
