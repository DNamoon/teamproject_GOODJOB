package com.goodjob.selfIntroduction.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * 박채원 22.10.03 작성
 */

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelfIntroductionDTO {
    private Long selfId;
    private String selfInterActivity;
    
    @NotNull(message = "자기소개서 항목을 작성해 주세요")
    private String selfLetter;
    private Long resumeId;
}
