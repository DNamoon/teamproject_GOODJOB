package com.goodjob.admin.postpaging;

import com.goodjob.notice.Notice;
import com.goodjob.post.Post;
import lombok.Data;

import java.util.List;

/**
 * 10.9 공고현황관리페이지를 위한 페이징관련 클래스 By.Oh
 */
@Data
public class ArticlePage {

    private Long total;
    private Long currentPage;
    private List<Post> postContent;
    private List<Notice> noticeContent;
    private Long totalPage;
    private Long startPage;
    private Long endPage;

    public ArticlePage(Long total, Long currentPage, Long size, List<Post> postContent) {
        this.total = total;
        this.currentPage = currentPage;
        this.postContent = postContent;
        if (total == 0) {
            totalPage = 0L;
            startPage = 0L;
            endPage = 0L;
        } else {
            totalPage = total / size;
            if (total % size > 0) {
                totalPage++;
            }
            Long modVal = currentPage % 5;
            startPage = currentPage / 5 * 5 + 1;
            if (modVal == 0) startPage -= 5;

            endPage = startPage + 4;
            if (endPage > totalPage) endPage = totalPage;
        }
    }

    public ArticlePage(Long total, Long currentPage, List<Notice> noticeContent, Long size) {
        this.total = total;
        this.currentPage = currentPage;
        this.noticeContent = noticeContent;
        if (total == 0) {
            totalPage = 0L;
            startPage = 0L;
            endPage = 0L;
        } else {
            totalPage = total / size;
            if (total % size > 0) {
                totalPage++;
            }
            Long modVal = currentPage % 5;
            startPage = currentPage / 5 * 5 + 1;
            if (modVal == 0) startPage -= 5;

            endPage = startPage + 4;
            if (endPage > totalPage) endPage = totalPage;
        }
    }

    public boolean hasNoArticles() {
        return total == 0;
    }

    public boolean hasArticles() {
        return total > 0;
    }

}
