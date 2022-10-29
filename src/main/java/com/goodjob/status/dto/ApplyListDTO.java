package com.goodjob.status.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    private String postName;
    private String companyName;
    private String resumeTitle;
}
