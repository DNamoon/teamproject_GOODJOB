package com.goodjob.status.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.sql.Date;

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
    private Date postEndDate;
}
