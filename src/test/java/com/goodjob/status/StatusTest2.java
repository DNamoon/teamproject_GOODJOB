package com.goodjob.status;

import com.goodjob.member.MemberRepository;
import com.goodjob.resume.ResumeRepository;
import com.goodjob.selfIntroduction.SelfIntroduction;
import com.goodjob.career.Career;
import com.goodjob.resume.certificate.Certificate;
import com.goodjob.resume.certificate.CertificateName;
import com.goodjob.resume.certificate.CertificateRepository;
import com.goodjob.education.Education;
import com.goodjob.education.MajorName;
import com.goodjob.education.SchoolName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.stream.LongStream;

@SpringBootTest
@Transactional
class StatusTest2 {

    @Autowired
    ResumeRepository resumeRepository;
    @Autowired
    SelfIntroductionRepository selfIntroductionRepository;
    @Autowired
    EducationRepository educationRepository;
    @Autowired
    CertificateRepository certificateRepository;
    @Autowired
    CareerRepository careerRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Commit
    void saveSelfIntroduction() {
        LongStream.rangeClosed(1, 10).forEach(i -> {
            SelfIntroduction selfIntroduction = new SelfIntroduction(i
                    , resumeRepository.findById(i).get()
                    , "Act" + i
                    , "Letter" + i);
            selfIntroductionRepository.save(selfIntroduction);
        });
    }

    @Test
    @Commit
    void saveEducation() {
        LongStream.rangeClosed(1, 3).forEach(i -> {
            Education education = new Education(i
                    , resumeRepository.findById(i).get()
                    , new SchoolName("sch_name_test" + i)
                    , new MajorName("major_name_test" + i)
                    , Date.valueOf("1010-10-10")
                    , "" + i);
            educationRepository.save(education);
        });
    }

    @Test
    @Commit
    void saveCertificate() {
        LongStream.rangeClosed(1, 3).forEach(i -> {
            Certificate certificate = new Certificate(i
                    , new CertificateName("certificate_name" + i, "certi_inst" + i)
                    , resumeRepository.findById(i).get()
                    , Date.valueOf("1010-10-10")
                    , "" + i);
            certificateRepository.save(certificate);
        });
    }

    @Test
    @Commit
    void saveCareer() {
        LongStream.rangeClosed(2, 3).forEach(i -> {
            Career career = new Career(i
                    , resumeRepository.findById(i).get()
                    , "companyname" + i
                    , "period" + i
                    , "task" + i);
            careerRepository.save(career);
        });
    }
    @Test
    void findResumeDetail(){

    }
}