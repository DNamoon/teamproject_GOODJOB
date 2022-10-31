package com.goodjob.Admin;

import com.goodjob.admin.Admin;
import com.goodjob.admin.AdminConst;
import com.goodjob.admin.apexchart.VisitorStatisticsRepository;
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
import java.time.LocalDate;
import java.util.List;
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

    @Test
    @Commit
    public void save() {
        Admin admin = new Admin(1L, "admin", "1234", AdminConst.ADMIN);
        adminRepository.save(admin);
    }

    @Test
    @Commit
    public void visitTest() {
//        int dayOfMonth = LocalDate.now().getDayOfMonth();
//        System.out.println("dayOfMonth = " + dayOfMonth);

//        VisitorStatistics visitorStatistics = new VisitorStatistics(LocalDate.of(2022,10,4), 34L);
//        visitorStatisticsRepository.save(visitorStatistics);
//        VisitorStatistics visitorStatistics2 = new VisitorStatistics(LocalDate.of(2022,10,5), 64L);
//        visitorStatisticsRepository.save(visitorStatistics2);
//        VisitorStatistics visitorStatistics3 = new VisitorStatistics(LocalDate.of(2022,10,6), 12L);
//        visitorStatisticsRepository.save(visitorStatistics3);

//        Long longs = visitorStatisticsRepository.sumVisitor();
//        System.out.println("aLong = " + longs);
//        visitorStatisticsRepository.updateVisitor(LocalDate.now());

//        LocalDate localDate = LocalDate.now().minusDays(7L);
//        System.out.println("localDateTime = " + localDate);

//        LocalDate startDate = LocalDate.of(2022, 10, 4);
//        LocalDate endDate = LocalDate.of(2022, 10, 6);
//        List<VisitorStatistics> allByXBetween = visitorStatisticsRepository.findAllByXBetween(startDate, endDate);
//        System.out.println("allByXBetween = " + allByXBetween);

//        List<VisitorStatistics> all = visitorStatisticsRepository.findAll();
//        VisitorStatistics visitorStatistics = all.get(0);
//        System.out.println("visitorStatistics = " + visitorStatistics);
    }
    @Test
    void pageTest(){
        Sort sort = Sort.by("noticeId").descending();
        Pageable pageable = PageRequest.of(0,10 , sort);
        Page<Notice> result = noticeRepository.findAll(pageable);
        for (Notice notice : result) {
            System.out.println("notice = " + notice);
        }
    }
    @Test
    @Commit
    void saveNotice(){
        LongStream.rangeClosed(6,50).forEach(i->{
            noticeRepository.save(new Notice(i,"test"+i,"contentTest"+i,LocalDate.now(),"0"));
        });
    }

    @Test
    void 공고페이지조회(){
        Sort sort = Sort.by("PostId").descending();
        List<Post> all = postRepository.findAll();
        Pageable pageable = PageRequest.of(1, 100, sort);
        Page<Post> allByPostId = postRepository.findAll(pageable);
        all.forEach(i-> System.out.println("i = " + i.getPostContent()));
    }

    @Test
    @Commit
    void 고객문의생성테스트(){
        CustomerInquiryPost build = CustomerInquiryPost.builder().inquiryPostCategory(CustomerInquiryPostType.ETC)
                .inquiryPostMemberId(memberRepository.findById(2L).get())
                .inquiryPostContent("content")
                .inquiryPostId(4L)
                .inquiryPostPublishedDate(Date.valueOf(LocalDate.now()))
                .inquiryPostStatus("0")
                .inquiryPostTitle("title")
                .inquiryPostWriter("writer")
                .build();
        customerInquiryPostRepository.save(build);
    }
    @Test
    void 멤버로문의조회(){
        Sort sort = Sort.by("inquiryPostId").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<CustomerInquiryPost> byCompany = customerInquiryPostRepository.findAllByInquiryPostComId(pageable, "test");
        Page<CustomerInquiryPost> byMember = customerInquiryPostRepository.findAllByInquiryPostMemberId(pageable, "test");
        System.out.println("test = " + byCompany.getContent().size());
        System.out.println("test = " + byMember.getContent().size());
    }
}
