package com.goodjob.resume.dto;

import lombok.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ResumeListDTO {
    private Long resumeId;
    private String resumeTitle;
    private Date resumeUpdateDate;
    private boolean submitted;
}
