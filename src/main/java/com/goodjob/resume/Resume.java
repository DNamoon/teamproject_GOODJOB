package com.goodjob.resume;

import com.goodjob.career.Career;
import com.goodjob.certification.Certification;
import com.goodjob.education.Education;
import com.goodjob.member.Member;
import com.goodjob.selfIntroduction.SelfIntroduction;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;


/**
 * 박채원 22.10.02 작성
 * 박채원 22.10.23 수정 - 이력서제목, 수정날짜 컬럼 추가
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@DynamicInsert
@EntityListeners(value = {AuditingEntityListener.class})
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;

    @Column(columnDefinition = "varchar(50) default '이력서'")
    private String resumeTitle;

    @Column
    @CreationTimestamp
    private Date resumeUpdateDate;

    @Column(length = 45)
    private String resumeMemPhone;

    @Column(length = 45)
    private String resumeMemEmail;

    @Column(length = 45)
    private String resumeMemAddress;

    @Column(length = 45)
    private String resumeMemName;

    @Column(length = 45)
    private String resumeMemGender;

    @Column
    private Date resumeMemBirthDate;

    @Column
    private boolean deleted;

    @Column(nullable = false)
    private boolean submitted;

    @ManyToOne
    @JoinColumn(name = "resumeMemId")
    private Member resumeMemId;

    //하나의 이력서 조회할 때 관련된 모든 항목을 불러오게 하려고 했는데 manytoone이 여러개 있어서 자꾸 오류남 - 하나만 있을 때는 정상작동
    @OneToMany(mappedBy = "resume", cascade = CascadeType.REMOVE)
    private List<Career> career;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.REMOVE)
    private List<Certification> certification;

    @OneToOne(mappedBy = "resume", cascade = CascadeType.REMOVE)
    private Education education;

    @OneToOne(mappedBy = "resume", cascade = CascadeType.REMOVE)
    private SelfIntroduction selfIntroduction;
}
