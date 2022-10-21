package com.goodjob.certification.dto;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * 박채원 22.10.03 작성
 */

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationDTO {
    private Long certiId;
    private Date certiGetDate;
    private String certiScore;
    private Long resumeId;
    private String certificateName;
}
