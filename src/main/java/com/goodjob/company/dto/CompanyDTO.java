package com.goodjob.company.dto;

import com.goodjob.company.Comdiv;
import com.goodjob.company.Company;
import com.goodjob.company.Region;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {

    private String comRegCode;
    private String comComdivCode;
    private String comLoginId;
    private String comPw;
    private String comPhone;
    private String comEmail;
    private String comName;
    private String comBusiNum; // 사업등록번호
    private String comInfo;
    private String comTerms;
    private String comAddress;  // 회사주소

    public Company toEntity() {
        Region region = Region.builder()
                .regCode(comRegCode)
                .build();

        Comdiv comdiv = Comdiv.builder()
                .comdivCode(comComdivCode)
                .build();

        Company com = Company.builder()
                .comLoginId(comLoginId)
                .comRegCode(region)
                .comComdivCode(comdiv)
                .comPw(comPw)
                .comPhone(comPhone)
                .comEmail(comEmail)
                .comName(comName)
                .comBusiNum(comBusiNum)
                .comInfo(comInfo)
                .comTerms(comTerms)
                .comAddress(comAddress)
                .build();
        return com;
    }
}
