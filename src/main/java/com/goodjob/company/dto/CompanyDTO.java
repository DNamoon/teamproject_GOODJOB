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
 *
 *  +2022.10.17
 *  로그인 폼 기업/개인 통일 위해 로그인, 비밀번호 창 name 통일. 필드도 loginId,pw 통일(46,51, 93, 96라인 변경)
 *
 *  +2022.10.24
 *  기업분류, 지역분류 타입 변경. (회원정보에서 코드와 이름으로 받아오기 위해서)
 */
package com.goodjob.company.dto;

import com.goodjob.company.Comdiv;
import com.goodjob.company.Company;
import com.goodjob.company.Region;
import lombok.*;

import javax.validation.constraints.NotBlank;
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
    //ho - 22.10.17 comLoginID -> loginId (로그인 폼 input name 통일. DTO 필드 loginId,pw 로 통일)
    private String loginId;

    @Size(min = 3, max = 25, message = "Password는 3~25자 사이여야 합니다.")
    //@NotBlank(message = "비밀번호는 필수항목입니다.")
    //ho - 22.10.17 comPw1 -> pw (로그인 폼 input name 통일. DTO 필드 loginId,pw 로 통일)
    private String pw;

    //비밀번호가 일치하지 않으면 넘어가지 않도록 하기 위해 엔티티와는 별개로 DTO에만 comPw2 추가
    //@NotBlank(message = "비밀번호 확인은 필수항목입니다.")
    private String comPw2;
//    @NotBlank
//    private Region comRegCode;
//    @NotBlank
//    private Comdiv comComdivCode;
    @NotBlank
    private String comRegCode;

    private String comRegName;
    @NotBlank
    private String comComdivCode;

    private String comComdivName;
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

        //2022.10.25 - 주소 4의 값 없을 때 보여줄 때 에러 발생 -> null일때  "null"을 DB에 넣기로 함.
        if(comAddress4.equals("")){
            comAddress4 = "null";
        }

        //ho - 22.10.17 getMemPw -> getPw (로그인 폼 input name 통일. DTO 필드 loginId,pw 로 통일) 93,96라인 변경
        Company com = Company.builder()
                .comLoginId(loginId)
                .comRegCode(region)
                .comComdivCode(comdiv)
                .comPw(pw)
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