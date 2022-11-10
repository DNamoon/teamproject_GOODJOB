/**
 * ho - 22.10.17 로그인 폼 통일위해
 * loginId,pw 필드 통일
 * 18,19,33,34라인 변경 (memLoginId->loginId , memPw->pw)
 */
package com.goodjob.member.memDTO;

import com.goodjob.member.Member;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@Builder
public class MemberDTO {
    private Long memId;

    @NotBlank(message = "사용자 ID는 필수항목입니다.")
    @Pattern(regexp = "[a-z0-9_-]{3,15}"
            ,message = "아이디는 3~15자의 영문 소문자와 숫자,특수기호(_),(-)만 사용 가능합니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수항목입니다.")
    @Pattern(regexp="(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{3,25}",
             message = "비밀번호는 3~25자로 최소 1개의 영문자, 숫자, 특수문자가 들어가야 합니다.")
    private String pw;
    private String pw2;
    @NotBlank(message = "전화번호는 필수항목입니다.")
    @Pattern(regexp="\\d{2,3}-\\d{3,4}-\\d{4}", message = "전화번호 형식이 맞지 않습니다. 다시 확인해주세요.")
    private String memPhone;
    @NotBlank(message = "이메일은 필수항목입니다.")
    @Pattern(regexp="[0-9a-zA-Z]([-_.]?[0-9a-zA-Z]){2,25}"
            , message = "이메일 형식이 맞지 않습니다. 다시 확인해주세요.")
    private String memEmail1;
    private String memEmail2;
    @NotBlank(message = "이름은 필수항목입니다.")
    private String memName;
    @NotNull(message = "생년월일은 필수항목입니다.")
    private Date memBirthDate;
    @NotBlank(message = "주소는 필수항목입니다.")
    private String memAddress;
    private String detailAddress;
    @NotBlank(message = "성별은 필수항목입니다.")
    private String memGender;
    @NotBlank(message = "개인정보 수집 동의는 필수항목입니다.")
    private String memTerms;

    public Member toEntity() {
        if(memAddress.equals("")){
            memAddress="null";
        }
        if(detailAddress.equals("")){
            detailAddress="null";
        }
        Member member = Member.builder()
                .memId(memId)
                .memLoginId(loginId)
                .memPw(pw)
                .memName(memName)
                .memPhone(memPhone)
                .memEmail(memEmail1+"@"+memEmail2)
                .memBirthDate(memBirthDate)
                .memAddress(memAddress +"@"+ detailAddress)
                .memGender(memGender)
                .memTerms(memTerms)
                .build();
        return member;
    }

    //22.11.01 ho 추가. 아이디 찾기용 엔터티 변환 메서드.
    public Member toEntityForFindId() {
        return Member.builder()
                .memName(memName)
                .memEmail(memEmail1)
                .memPw(pw)
                .build();
    }

}
