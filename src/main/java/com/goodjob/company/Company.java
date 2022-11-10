/**
 * HO - 2022.10.05
 * 26라인 밑에 comMemdivCode(회원분류코드) 필요 없어짐에 따라 삭제
 * 59라인 회사주소 넣기 위해 comAddress 컬럼 추가
 * 37라인 comLoginId - unique 제약조건 추가(아이디 중복 제한)
 * 걸리는거!
 * create-drop하고 다시 테이블 생성했는데 comLoginId에 unique 제약조건 없는것 같음
 * ->확인결과 unique 제약조건 걸려 있음.
 *
 * +22.11.06 기업 회원 지역 테이블 삭제 -> Region타입 comRegCode '지역분류코드'필드 삭제.
 */
package com.goodjob.company;

import com.goodjob.customerInquiry.CustomerInquiryPost;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comId;

    @Column(unique = true, columnDefinition = "varbinary(128)")  //로그인 아이디 중복 허용 안 하기 위해서 엔티티에서 제한.
    private String comLoginId;

    @Column
    private String comPw;

    @Column
    private String comPhone;

    @Column(unique = true, columnDefinition = "varbinary(128)")
    private String comEmail;

    @Column(columnDefinition = "varbinary(128)")
    private String comName;

    @OneToOne
    @JoinColumn(name = "comComdivCode")
    private Comdiv comComdivCode; // 회사분류코드

    @Column
    private String comBusiNum; // 사업등록번호

    @Column(length = 5000)
    private String comInfo;

    @Column(length = 2)
    private String comTerms;

    //22.10.05 ho - 회사 주소 넣기 위해 컬럼 추가
    @Column
    private String comAddress;

    // 오성훈 22.10.30 cascade 연관관계 추가 및 cascade 확인완료
//    @OneToMany(mappedBy = "postComId", cascade = CascadeType.ALL)
//    private List<Post> memResume = new ArrayList<>();

    @OneToMany(mappedBy = "inquiryPostComId", cascade = CascadeType.ALL)
    private List<CustomerInquiryPost> customerInquiryPosts = new ArrayList<>();

    // 더미데이터 생성을 위한 임시 생성자 by.OH
    public Company(Comdiv comComdivCode, String comLoginId, String comPw) {
        this.comComdivCode = comComdivCode;
        this.comLoginId = comLoginId;
        this.comPw = comPw;
    }
    /** 22.10.29 김도현 추가
     * 비밀번호 변경
     * **/
    public void updatePassword(String password){
        this.comPw = password;
    }

}
