package com.goodjob.status.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;


/**
 * 박채원 22.11.05 작성
 */

@Data
@Builder
public class IntervieweeListDTO {
    private Long statId;
    private Long statPostId;
    private Long statResumeId;
    private String statPass;
    private String applierName;
    private String postOccupation;
    private String interviewPlace;
    private LocalDateTime interviewDate;
}
