package com.goodjob.post.postdto;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.sql.Date;
import java.util.List;

/**
 * 22.10.26 saveForm 클래스 By.OH
 */
@Data
@Builder
public class PostInsertDTO {

    @Positive(message = "invalid post id")
    private Long id; // 공고 pk

    @NotEmpty
    @Size(max = 50)
    private String postTitle; //공고 제목

    @Positive
    private Long postOccCode; // 공고 지역코드
    @NotEmpty
    @Max(100)
    private String postRecruitNum; // 공고 모집인원
    @NotEmpty
    private String postGender; // 공고 성별 (남,여,무관)
    @Future
    private Date postStartDate; // 공고 시작일
    @Future
    private Date postEndDate; // 공고 종료일

    private List<MultipartFile> postImg;
    @NotEmpty
    @Size(min = 5,max = 5)
    private String postcode; //우편번호
    @NotEmpty
    @Size(max = 255)
    private String postAddress; // 주소1
    @NotEmpty
    @Size(max = 255)
    private String postDetailAddress; // 주소2
    @Nullable
    private String etc; // 참고사항
    @Positive
    private Long postSalaryId; // 연봉대
    @NotEmpty
    @Size(max = 5000)
    private String postContent; // 공고 내용
    @Nullable
    private String comLoginId; // 회사 로그인 id
}