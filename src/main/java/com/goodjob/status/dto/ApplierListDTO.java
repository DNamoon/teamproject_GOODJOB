package com.goodjob.status.dto;

import com.goodjob.post.Post;
import com.goodjob.resume.Resume;
import com.goodjob.status.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ApplierListDTO {
    private Long statId;
    private Long statPostId;
    private Long statResumeId;
    private String statPass;
    private Date statApplyDate;
    private String applierId;
    private String applierName;
    private String postTitle;
    private String postOccupation;
}
