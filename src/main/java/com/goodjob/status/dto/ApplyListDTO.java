package com.goodjob.status.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.sql.Date;

/**
 * 박채원 22.10.26 작성
 */

@Data
@AllArgsConstructor
@Builder
public class ApplyListDTO {
    private Long statId;
    private Long statPostId;
    private Long statResumeId;
    private String statPass;
    private Date statApplyDate;
    @Builder.Default
    private String postName = "탈퇴한 회사의 공고입니다.";
    @Builder.Default
    private String companyName = "탈퇴한 회사";
    private String resumeTitle;
}
