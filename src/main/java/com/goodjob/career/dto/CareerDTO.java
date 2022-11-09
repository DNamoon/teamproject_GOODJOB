package com.goodjob.career.dto;

import lombok.*;
import java.sql.Date;

/**
 * 박채원 22.10.03 작성
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CareerDTO {
    private Long careerId;
    private String careerCompanyName;
    private Date careerRetireDate;
    private Date careerJoinedDate;
    private String careerTask;
    private Long resumeId;
}
