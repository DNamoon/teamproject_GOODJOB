package com.goodjob.member.memDTO;

import lombok.*;

import java.sql.Date;

/**
 * 박채원 22.10.02 작성
 */

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeMemberDTO {
    private Long memId;
    private String memLoginId;
    private String memFirstPhoneNum;
    private String memMiddlePhoneNum;
    private String memLastPhoneNum;
    private String memFirstEmail;
    private String memLastEmail;
    private String memName;
    private Date memBirthDate;
    private String memFirstAddress;
    private String memLastAddress;
    private String memGender;
}
