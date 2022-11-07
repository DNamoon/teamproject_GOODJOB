package com.goodjob.status.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 박채원 22.11.01 작성
 */

@Data
@AllArgsConstructor
@Builder
public class SendMailDTO {
    private String statPass;
    private String applierEmail;
    private String applierName;
    private String companyName;
    private String postName;
}
