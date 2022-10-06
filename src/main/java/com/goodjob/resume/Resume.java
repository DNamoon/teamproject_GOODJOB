package com.goodjob.resume;

import com.goodjob.certification.Certification;
import com.goodjob.education.Education;
import com.goodjob.member.Member;
import com.goodjob.selfIntroduction.SelfIntroduction;
import lombok.*;

import javax.persistence.*;
import java.util.List;


/**
 * 박채원 22.10.02 작성
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;

    @Column(length = 45)
    private String resumeMemPhone;

    @Column(length = 45)
    private String resumeMemEmail;

    @Column(length = 45)
    private String resumeMemAddress;

    @ManyToOne
    @JoinColumn(name = "resumeMemId", nullable = false)
    private Member resumeMemId;


    //하나의 이력서 조회할 때 관련된 모든 항목을 불러오게 하려고 했는데 manytoone이 여러개 있어서 자꾸 오류남 - 하나만 있을 때는 정상작동
//    @OneToMany(mappedBy = "resume", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
//    private List<Career> career;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Certification> certification;

    @OneToOne(mappedBy = "resume", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Education education;

    @OneToOne(mappedBy = "resume", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private SelfIntroduction selfIntroduction;
}
