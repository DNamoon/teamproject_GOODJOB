/**
 *  HO - 2022.10.05
 *  기업 회원가입을 위한 DTO
 *  18라인 @Setter 추가
 *  +뷰 단에서도 입력조건 주고 여기에서도 검증조건 추가
 *  +@Size,@NotEmpty,@Email 등 검증조건 추가 (spring-boot-starter-validation) 관련
 *  * Gradle에 implementation 'org.springframework.boot:spring-boot-starter-validation' 의존성
 */
package com.goodjob.company.dto;

import com.goodjob.company.Comdiv;
import com.goodjob.company.Company;
import com.goodjob.company.Region;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {

    //DTO 클래스에서는 조인컬럼이라도 필드 다 String이나 Long으로 받아야 함.
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자 ID는 필수항목입니다.")
    private String comLoginId;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String comPw1;

    //비밀번호가 일치하지 않으면 넘어가지 않도록 하기 위해 엔티티와는 별개로 DTO에만 comPw2 추가
    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private  String comPw2;
    @NotEmpty
    private String comRegCode;
    @NotEmpty
    private String comComdivCode;
    @NotEmpty
    private String comPhone;
    @NotEmpty
    private String comName;
    @NotEmpty
    private String comBusiNum; // 사업등록번호
    @NotEmpty
    private String comInfo;
    @NotEmpty
    private String comTerms;
    @NotEmpty
    private String comAddress;  // 회사주소
    @NotEmpty
    @Email
    private String comEmail;

    public Company toEntity() {
        //엔티티 바꿀 때는 builder이용해서 필요한 객체 만들자.
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
                .comPw(comPw1)
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
