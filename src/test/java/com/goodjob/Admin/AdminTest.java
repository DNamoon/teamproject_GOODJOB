package com.goodjob.Admin;

import com.goodjob.admin.Admin;
import com.goodjob.admin.AdminConst;
import com.goodjob.admin.apexchart.VisitorStatisticsRepository;
import com.goodjob.admin.customerInquiry.CustomerInquiryPostAnswerDTO;
import com.goodjob.company.Company;
import com.goodjob.customerInquiry.CustomerInquiryPost;
import com.goodjob.customerInquiry.repository.CustomerInquiryPostRepository;
import com.goodjob.customerInquiry.CustomerInquiryPostType;
import com.goodjob.admin.repository.AdminRepository;
import com.goodjob.company.repository.CompanyRepository;
import com.goodjob.member.repository.MemberRepository;
import com.goodjob.notice.Notice;
import com.goodjob.notice.NoticeRepository;
import com.goodjob.post.Post;
import com.goodjob.post.repository.PostRepository;
import com.goodjob.post.service.PostService;
import org.apache.ibatis.annotations.Update;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

@SpringBootTest
@Transactional
public class AdminTest {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    VisitorStatisticsRepository visitorStatisticsRepository;
    @Autowired
    NoticeRepository noticeRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CustomerInquiryPostRepository customerInquiryPostRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostService postService;

    @Test
    @Commit
    public void save() {
        Admin admin = new Admin(1L, "admin", "1234", AdminConst.ADMIN);
        adminRepository.save(admin);
    }

    @Test
    @Commit
    void saveNotice() {
        LongStream.rangeClosed(6, 50).forEach(i -> {
            noticeRepository.save(new Notice(i, "test" + i, "contentTest" + i, LocalDate.now(), "0"));
        });
    }

    @Test
    void 공고페이지조회() {
        Sort sort = Sort.by("PostId").descending();
        List<Post> all = postRepository.findAll();
        Pageable pageable = PageRequest.of(1, 100, sort);
        Page<Post> allByPostId = postRepository.findAll(pageable);
        all.forEach(i -> System.out.println("i = " + i.getPostContent()));
    }

    @Test
    @Commit
    void 고객문의생성테스트() {
        LongStream.rangeClosed(12, 72).forEach(i -> {
            CustomerInquiryPost build = CustomerInquiryPost.builder().inquiryPostCategory(CustomerInquiryPostType.GENERAL)
                    .inquiryPostMemberId(memberRepository.findById(1L).get())
                    .inquiryPostContent("content")
                    .inquiryPostId(i)
                    .inquiryPostPublishedDate(LocalDateTime.now())
                    .inquiryPostStatus("0")
                    .inquiryPostTitle("title")
                    .inquiryPostWriter("writer")
                    .build();
            customerInquiryPostRepository.save(build);
        });

    }

    @Test
    void 멤버로문의조회() {
        Sort sort = Sort.by("inquiryPostId").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<CustomerInquiryPost> byCompany = customerInquiryPostRepository.findAllByInquiryPostComId(pageable, "test");
        Page<CustomerInquiryPost> byMember = customerInquiryPostRepository.findAllByInquiryPostMemberId(pageable, "test");
        System.out.println("test = " + byCompany.getContent().size());
        System.out.println("test = " + byMember.getContent().size());
    }

    @Test
    void 개인만조회및정렬() {
        Sort sort = Sort.by("inquiryPostId").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<CustomerInquiryPost> allByInquiryPostComIdIsNull = customerInquiryPostRepository.findAllByInquiryPostComIdIsNull(pageable);
        allByInquiryPostComIdIsNull.getContent().forEach(i -> System.out.println("i = " + i.getInquiryPostComId()));
    }

    @Test
    void 카테고리검색() {
        Sort sort = Sort.by("inquiryPostId").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<CustomerInquiryPost> etc = customerInquiryPostRepository.findAllByCategory(pageable, CustomerInquiryPostType.GENERAL);
        for (CustomerInquiryPost customerInquiryPost : etc) {
            System.out.println("customerInquiryPost = " + customerInquiryPost.getInquiryPostTitle());
        }
    }

    @Test
    void 미답변테스트() {
        Sort sort = Sort.by("inquiryPostId").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<CustomerInquiryPost> allByInquiryPostStatus = customerInquiryPostRepository.findAllByInquiryPostStatus(pageable, "0");
        for (CustomerInquiryPost byInquiryPostStatus : allByInquiryPostStatus) {
            System.out.println("byInquiryPostStatus = " + byInquiryPostStatus.getInquiryPostTitle());
        }
        System.out.println("customerInquiryPostRepository.countByInquiryPostStatus(\"0\") = " + customerInquiryPostRepository.countByInquiryPostStatus("0"));
    }

}
