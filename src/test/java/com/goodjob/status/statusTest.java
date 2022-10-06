package com.goodjob.status;

import com.goodjob.company.Comdiv;
import com.goodjob.company.Company;
import com.goodjob.company.CompanyRepository;
import com.goodjob.company.Region;
import com.goodjob.member.Member;
import com.goodjob.member.MemberRepository;
import com.goodjob.post.Occupation;
import com.goodjob.post.Post;
import com.goodjob.post.PostRepository;
import com.goodjob.resume.Resume;
import com.goodjob.resume.ResumeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.stream.LongStream;

@SpringBootTest
@Transactional
class statusTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ResumeRepository resumeRepository;
    @Autowired
    StatusRepository statusRepository;


    @Test
    @Commit
    void saveMember() {
        LongStream.rangeClosed(11, 20).forEach(i -> {

            Member member = new Member(i
                    , "test" + i
                    , "1234"
                    , "01012341234"
                    , "test@test.com"
                    , "testuser"
                    , Date.valueOf("1010-10-10")
                    , "address"
                    , "M"
                    , "1");
            memberRepository.save(member);
        });
    }

    @Test
    @Commit
    void saveCompany() {
        Comdiv comdiv = new Comdiv("기업분류테스트1", "test");
        LongStream.rangeClosed(1, 10).forEach(i -> {
            Company company = new Company(i
                    , new Region(2L, "서울특별시")
                    , comdiv
                    , "test" + i
                    , "1234"
                    , "01012341234"
                    , "test@test.com"
                    , "testcompany"
                    , "01023412" + i
                    , "info"
                    , "1");
            companyRepository.save(company);
        });
    }

    @Test
    @Commit
    void savePost() {
        Company company = companyRepository.findById(1L).get();

        Date date = Date.valueOf("1010-10-10");
        Date date1 = Date.valueOf("1010-10-10");
        LongStream.rangeClosed(1, 10).forEach(i -> {
            Post post = new Post(i
                    , "title"
                    , new Occupation("15", "제조")
                    , company
                    , "con"
                    , "1"
                    , date, date1
                    , "M");
            postRepository.save(post);
        });
    }

    @Test
    @Commit
    void saveResume() {
        LongStream.rangeClosed(1, 10).forEach(i -> {
//            Resume resume = new Resume(i, memberRepository.findById(i).get());
//            resumeRepository.save(resume);
        });
    }

    @Test
    @Commit
    void saveResumeV2() {
//        Resume resume = new Resume(11L, memberRepository.findById(1L).get());
//        resumeRepository.save(resume);
    }

    @Test
    @Commit
    void saveStatus() {
        LongStream.rangeClosed(1, 10).forEach(i -> {
                    Status status = new Status(i, postRepository.findById(i).get(), resumeRepository.findById(i).get());
                    statusRepository.save(status);
                }
        );
    }

    @Test
    @Commit
    void saveStatusV2() {
        Status status = new Status(14L, postRepository.findById(2L).get(), resumeRepository.findById(1L).get());
        statusRepository.save(status);
    }

    @Test
    void findResume(){
        List<Resume> result = resumeRepository.findByResumeMemId(memberRepository.findById(1L).get());
        result.stream().forEach(i-> System.out.println("i = " + i.getResumeId() + i.getResumeMemId().getMemLoginId()));
    }
    @Test
    void existsResumeMemId(){
        Member member = memberRepository.findById(1L).get();
        Post post = postRepository.findById(2L).get();

        boolean exists = statusRepository.existsByStatResumeId_ResumeMemId(member);
        System.out.println("exists = " + exists);

        boolean b = statusRepository.existsByStatResumeId_ResumeMemIdAndStatPostId(member, post);
        System.out.println("b = " + b);
    }
    @Test
    @Commit
    void deleteMember(){
        memberRepository.deleteById(2L);
    }
}