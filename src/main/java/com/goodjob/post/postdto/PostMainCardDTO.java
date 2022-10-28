package com.goodjob.post.postdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostMainCardDTO {

    private Long id; // 공고 pk
    private String title; // 공고 제목
    private String regionName; // 공고 근무지
    private String salaryRange; // 공고 연봉대
    private String remainDay; // 공고 남은날짜

    private String occName; // 공고 직종명
    private String comName; // 공고 회사명


}
