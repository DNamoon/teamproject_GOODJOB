/**
 *  HO - 2022.10.05
 *  기업 회원가입을 위한 DTO
 *  18라인 @Setter 추가
 *  +뷰 단에서도 입력조건 주고 여기에서도 검증조건 추가
 *  +@Size,@NotEmpty,@Email 등 검증조건 추가 (spring-boot-starter-validation) 관련
 *  +@NotEmpty ->@NotBlank로 변경 (null, "", " " 모두 허용 안 하기 위해서)
 *
 *  * Gradle에 implementation 'org.springframework.boot:spring-boot-starter-validation' 의존성
 *
 *  + 2022.10.06
 *  comAddress1,2,3,4 추가
 *  - 뒤로가기나 새로고침시 기존 comAddress가 하나인 경우에 모둔 우편번호창, 상세주소창 등에 모든 주소가 재입력 되는 문제 발생.
 *  - 창마다 name을 따로 주어 받아오게 해 문제해결.
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
import javax.validation.constraints.NotBlank;
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
    @Size(min = 3, max = 15, message = "ID는 3~15자 사이여야 합니다.")
    @NotBlank(message = "사용자 ID는 필수항목입니다.")
    private String comLoginId;

    @Size(min = 3, max = 25, message = "Password는 3~25자 사이여야 합니다.")
    @NotBlank(message = "비밀번호는 필수항목입니다.")
    private String comPw1;

    //비밀번호가 일치하지 않으면 넘어가지 않도록 하기 위해 엔티티와는 별개로 DTO에만 comPw2 추가
    @NotBlank(message = "비밀번호 확인은 필수항목입니다.")
    private  String comPw2;
    @NotBlank
    private String comRegCode;
    @NotBlank
    private String comComdivCode;
    @NotBlank
    private String comPhone;
    @NotBlank
    private String comName;
    @NotBlank
    private String comBusiNum; // 사업등록번호
    @NotBlank
    private String comInfo;
    @NotBlank
    private String comTerms;
    @NotBlank
    private String comAddress1;  // 회사주소(우편번호)
    @NotBlank
    private String comAddress2;  // 회사주소(주소)
    @NotBlank
    private String comAddress3;  // 회사주소(상세주소)

    private String comAddress4;  // 회사주소(참고항목)
    @NotBlank
    private String comEmail1;
    private String comEmail2;

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
                .comEmail(comEmail1+"@"+comEmail2)
                .comName(comName)
                .comBusiNum(comBusiNum)
                .comInfo(comInfo)
                .comTerms(comTerms)
                .comAddress(comAddress1+"@"+comAddress2+"@"+comAddress3+"@"+comAddress4)
                .build();
        return com;
    }
}