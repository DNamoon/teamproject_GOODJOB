package com.goodjob.post.postdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.List;

// 공고 조회에서 사용할 DTO
@Builder
@AllArgsConstructor
@Data
public class PostDetailsDTO {
    private Long postId; // 공고 pk
    private String title; // 공고 제목
    private String content; // 공고 내용
    private String startDate; // 공고 시작일
    private String endDate; // 공고 마감일
    private String remainDay; // d-day
    private String salary; // 공고 연봉대
    private String recruitNum; // 모집 인원
    private String postAddress1; // 주소1
    private String postAddress2; // 주소2
    private String postGender; // 성별
    private List<String> attachment; // 첨부파일

    private String occName; // 공고 직종명
    private String comName; // 공고 회사명

}
