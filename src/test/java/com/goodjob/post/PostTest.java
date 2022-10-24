package com.goodjob.post;

import com.goodjob.admin.postpaging.ArticlePage;
import com.goodjob.admin.postpaging.ArticlePageService;
import com.goodjob.company.Comdiv;
import com.goodjob.company.Company;
import com.goodjob.company.Region;
import com.goodjob.company.repository.CompanyRepository;
import com.goodjob.post.occupation.Occupation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
@Slf4j
@Transactional
class PostTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ArticlePageService articlePageService;

    @Test
    @Commit
    void savePost() {
            // 더미 데이터 입력용 메소드
            java.sql.Date d = java.sql.Date.valueOf(LocalDate.now());
            Region rg = Region.builder()
                    .regCode("02")
                    .regName("서울특별시")
                    .build();

            Comdiv cd = Comdiv.builder()
                    .comdivCode("Major")
                    .comdivName("대기업")
                    .build();

            Occupation occ = Occupation.builder()
                    .occId(1L)
                    .occCode("11")
                    .occName("공공 기관 및 기업 고위직")
                    .build();

            if(!(postRepository.findById(1L).isPresent())){
                Date endDate = Date.valueOf(LocalDate.now().plusDays(7));
                Date startDate = Date.valueOf(LocalDate.now());

                IntStream.rangeClosed(1,20).forEach(i -> {

                    Company com = Company.builder()
                            .comRegCode(rg)
                            .comLoginId("sampleLoginId"+i)
                            .comPw("{bcrypt}$2a$10$Y7ZqygSi.3DkCoBmzL5rUOBiJ8HGhw/tWpBaXKxfc74RFsBJSN5aG")
                            .comPhone("samplePhone"+i)
                            .comName("sampleComName"+i)
                            .comComdivCode(cd)
                            .comEmail("sample@naver.com")
                            .comBusiNum("sampleBizNum"+i)
                            .comInfo("sampleComInfo"+i)
                            .comTerms("sa")
                            .comAddress("smaepleAdd"+i)
                            .build();
                    companyRepository.save(com);

                    Post post = Post.builder()
                            .postTitle("제목..."+i)
                            .postContent("내용입니다..."+i)
                            .postRecruitNum(""+10+i)
                            .postGender("남성")
                            .postStartDate(startDate)
                            .postEndDate(endDate)
                            .postOccCode(occ)
                            .postComId(com)
                            .state("모집 중")
                            .build();

                    postRepository.save(post);


                });
            }



//        for (long p = 106; p <= 115; p++) {
//            Date endDate = Date.valueOf(LocalDate.now().plusDays(7));
//            Date startDate = Date.valueOf(LocalDate.now());
//
//            Post post = new Post("title" + p, new Occupation(null,"25", "군인"), companyRepository.findById(1L).get(), "test" + p, "p", startDate, endDate, "M");
//            postRepository.save(post);
//        }
    }

    @Test
    void 채용현황테스트() {
        Long allCount = postRepository.count();
        Long endPostCount = postRepository.countAllByPostEndDateBefore(Date.valueOf(LocalDate.now()));

        Long progressPostCount = allCount - endPostCount;
        System.out.println("progressPostCount = " + progressPostCount);
    }

    @Test
    void 공고리스트조회() {
//        List<Post> result = postRepository.findAllByPostIdBetweenOrderByPostIdDesc(1L, 10L);
//        result.stream().forEach(i -> System.out.println("i = " + i.toString()));
    }
    @Test
    void 관리자페이지공고게시판테스트(){
        ArticlePage articlePage = articlePageService.getArticlePage(1L);
        log.info("getArticlePage = {}" , articlePage);
    }
}