package com.goodjob.resume.dto;

import lombok.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

/**
 * 박채원 22.10.05 작성
 * 박채원 22.10.13 수정 - validation 추가
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ResumeDTO {
    private String memName;
    private String memGender;
    private Date memBirthDate;
    private String memFirstAddress;
    private String memLastAddress;
    private String memFirstEmail;
    private String memLastEmail;
    private String memFirstPhoneNum;
    private String memMiddlePhoneNum;
    private String memLastPhoneNum;
    private boolean deleted;
    private boolean submitted;
    private Long memId;
}
