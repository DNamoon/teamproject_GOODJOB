package com.goodjob.post.postdto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Builder
@Data
public class PostSaveDto {
    // 공고 저장용("/p/save")

    private Long postId; // 공고 pk
    private String postTitle; // 공고 제목
    private String postContent; // 공고 내용
    private String postRecruitNum; // 공고 모집인원
    private Date postStartDate; // 공고 시작일
    private Date postEndDate; // 공고 종료일
    private String postGender; // 공고 모집 성별

    private Long occId; // 직종 pk


}
