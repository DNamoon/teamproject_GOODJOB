package com.goodjob.status.dto;

import lombok.Data;

import java.sql.Date;

/**
 * 박채원 22.10.26 작성
 */

@Data
public class ApplyDTO {
    private Long statId;
    private Date statApplyDate;
    private Long statPostId;
    private Long statResumeId;
}
