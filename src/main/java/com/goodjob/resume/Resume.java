package com.goodjob.resume;

import com.goodjob.member.Member;
import com.goodjob.resume.career.Career;
import com.goodjob.resume.certificate.Certificate;
import com.goodjob.resume.education.Education;
import com.goodjob.resume.selfIntroduction.SelfIntroduction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resumeMemId")
    private Member resumeMemId;

    @OneToMany(mappedBy = "careerResumeId", cascade = CascadeType.ALL)
    private List<Career> resumeCareer;

    @OneToMany(mappedBy = "certiResumeId", cascade = CascadeType.ALL)
    private List<Certificate> resumeCertificate;

    @OneToOne(mappedBy = "eduResumeId", cascade = CascadeType.ALL)
    private Education resumeEducation;

    @OneToOne(mappedBy = "selfResumeId", cascade = CascadeType.ALL)
    private SelfIntroduction resumeSelfIntroduction;
}
