package com.goodjob.post;

import com.goodjob.admin.postpaging.ArticlePage;
import com.goodjob.admin.postpaging.ArticlePageService;
import com.goodjob.company.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        for (long p = 106; p <= 115; p++) {
            Date endDate = Date.valueOf(LocalDate.now().plusDays(7));
            Date startDate = Date.valueOf(LocalDate.now());

            Post post = new Post("title" + p, new Occupation("25", "군인"), companyRepository.findById(1L).get(), "test" + p, "p", startDate, endDate, "M");
            postRepository.save(post);
        }
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