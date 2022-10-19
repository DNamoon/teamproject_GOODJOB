package com.goodjob.admin.postpaging;

import com.goodjob.notice.Notice;
import com.goodjob.notice.NoticeRepository;
import com.goodjob.post.Post;
import com.goodjob.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 10.9 공고현황관리페이지를 위한 페이징관련 클래스 By.Oh
 */
@Service
@RequiredArgsConstructor
public class ArticlePageService {

    private final PostRepository postRepository;
    private Long size = 10L;

    public ArticlePage getArticlePage(Long pageNum) {
        long totalCount = postRepository.count();
        List<Post> content = postRepository.findAllByPostIdBetweenOrderByPostIdDesc
                (totalCount - (size * pageNum), totalCount - (size * (pageNum - 1)));
        return new ArticlePage(totalCount, pageNum, size, content);
    }

}
