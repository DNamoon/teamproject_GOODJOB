package com.goodjob.post.postdto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

/**
 * 22.10.26 saveForm 클래스 By.OH
 */
@Data
@Builder
public class PostInsertDTO {

    private Long id; // 공고 pk

    private String postTitle; //공고 제목

    private Long postOccCode; // 공고 지역코드

    private String postRecruitNum; // 공고 모집인원

    private String postGender; // 공고 성별 (남,여,무관)

    private Date postStartDate; // 공고 시작일

    private Date postEndDate; // 공고 종료일


    private List<MultipartFile> postImg;
    private String postcode; //우편번호

    private String postAddress; // 주소1

    private String postDetailAddress; // 주소2

    private String etc; // 참고사항

    private Long postSalaryId; // 연봉대

    private String postContent; // 공고 내용

    private String comLoginId; // 회사 로그인 id






















//    private Long postComId; // 회사 pk

//    private String comDivName; // 회사분류

//    private String ComLoginId;

}