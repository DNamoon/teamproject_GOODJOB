package com.goodjob.resume.dto;

import lombok.*;

/**
 * 박채원 22.10.05 작성
 */

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDTO {
    private String memFirstAddress;
    private String memLastAddress;
    private String memFirstPhoneNum;
    private String memMiddlePhoneNum;
    private String memLastPhoneNum;
    private String memFirstEmail;
    private String memLastEmail;
    private Long memId;
}
