package com.goodjob.certification.dto;

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
public class CertificationDTO {
    private Long certiId;
    private Date certiGetDate;
    private String certiScore;
    private Long resumeId;
    private String certificateName;
}
