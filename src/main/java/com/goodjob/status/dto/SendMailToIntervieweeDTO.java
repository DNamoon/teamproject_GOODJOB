package com.goodjob.status.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 박채원 22.11.05 작성
 */

@Data
@Builder
public class SendMailToIntervieweeDTO {
    private Long statId;
    private String applierEmail;
    private String applierName;
    private String companyName;
    private String postName;
    private String interviewPlace;
    private LocalDateTime interviewDate;
}
