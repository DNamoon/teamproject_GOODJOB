package com.goodjob.company;

import com.goodjob.member.Memberdiv;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comId;

    @OneToOne
    @JoinColumn(name = "comRegCode")
    private Region comRegCode; // 지역분류코드

    @OneToOne
    @JoinColumn(name = "comMemdivCode")
    private Memberdiv comMemdivCode; // 회원분류코드

    @OneToOne
    @JoinColumn(name = "comComdivCode")
    private Comdiv comComdivCode; // 회사분류코드

    @Column
    private String comLoginId;

    @Column
    private String comPw;

    @Column
    private String comPhone;

    @Column
    private String comEmail;

    @Column
    private String comName;

    @Column
    private String comBusiNum; // 사업등록번호

    @Column(length = 5000)
    private String comInfo;

    @Column(length = 1)
    private String comTerms;
}
