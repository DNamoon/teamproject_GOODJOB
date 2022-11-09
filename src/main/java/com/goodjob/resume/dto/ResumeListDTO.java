package com.goodjob.resume.dto;

import lombok.*;
import java.sql.Date;

@Data
@Builder
public class ResumeListDTO {
    private Long resumeId;
    private String resumeTitle;
    private Date resumeUpdateDate;
}
