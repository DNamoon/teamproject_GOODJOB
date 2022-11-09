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

    @NotNull(message = "졸업날짜를 작성해 주세요")
    private Date eduGraduationDate;

    @NotNull(message = "학교를 선택해 주세요")
    private String schoolName;

    @NotNull(message = "전공을 선택해 주세요")
    private String majorName;

    private String eduGetCredit;
    private String eduTotalCredit;
    private Long resumeId;

}
