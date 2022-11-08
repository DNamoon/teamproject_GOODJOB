package com.goodjob.post.postdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostComMyPageDTO {
    private Long postId; // 공고 pk
    private String title; // 공고 제목
    private String regionName; // 공고 근무지
    private String salaryRange; // 공고 연봉대
    private String startDate; // format( "2000년 08월 19일")
    private String endDate; // format( "2000년 08월 19일(D-5)", "2000년 08월 19일(오늘 마감)")
    private String recruitNum; // 공고 모집인원
    private String gender; // 공고 모집 성별 "남자", "여자", "성별무관"
    private String address; // 공고 근무처
    private int count; // 공고 조회수
    private String attachmentFileName; // 첨부파일 파일 이름

    private String region; // 공고 근무처
    private String occName; // 공고 직종명
    private String comName; // 공고 회사명
}
