package com.goodjob.education.dto;

import lombok.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * 박채원 22.10.03 작성
 */

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EducationDTO {
    private Long eduId;
    private Date eduGraduationDate;
    private String schoolName;
    private String majorName;
    private String eduGetCredit;
    private String eduTotalCredit;
    private Long resumeId;

}
