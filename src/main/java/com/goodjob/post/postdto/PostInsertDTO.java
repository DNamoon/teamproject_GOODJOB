package com.goodjob.post.postdto;

import com.goodjob.post.PostInsertForm;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
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
@PostInsertForm
@NoArgsConstructor
@AllArgsConstructor
public class PostInsertDTO {

    @Positive(message = "Id must be bigger than zero")
    private Long id; // 공고 pk

    @Size(max = 50,message = "Max length(50)")
    @NotEmpty(message = "Empty Title")
    private String postTitle; //공고 제목

    @Positive(message = "Id must be bigger than zero")
    @Max(value = 64,message = "Occupation code is not found")
    private Long postOccCode; // 공고 직종코드
    @NotEmpty(message = "Empty String is not allowed")
    private String postRecruitNum; // 공고 모집인원
    @NotEmpty(message = "Empty String is not allowed")
    private String postGender; // 공고 성별 (남,여,무관)
    private Date postStartDate; // 공고 시작일
    private Date postEndDate; // 공고 종료일

    private List<MultipartFile> postImg;
    @NotEmpty
    @Size(min = 5,max = 5,message = "The length of zipcode must be five")
    private String postcode; //우편번호
    @NotEmpty(message = "Empty String is not allowed")
    @Size(max = 255,message = "The length of address has exceeded the limit(255)")
    private String postAddress; // 주소1
    @NotEmpty(message = "Empty String is not allowed")
    @Size(max = 255,message = "The length of title has exceeded the limit(255)")
    private String postDetailAddress; // 주소2
    @Nullable
    private String etc; // 참고사항
    @Positive(message = "SalaryId must be bigger than zero")
    @Max(value=14,message = "SalaryId is not found")
    private Long postSalaryId; // 연봉대
    @NotEmpty(message = "Empty String is not allowed")
    @Size(max = 5000,message = "The length of address has exceeded the limit(5000)")
    private String postContent; // 공고 내용
    @Nullable
    private String comLoginId; // 회사 로그인 id
}