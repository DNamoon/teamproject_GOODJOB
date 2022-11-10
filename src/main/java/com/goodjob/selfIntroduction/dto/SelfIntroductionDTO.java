package com.goodjob.selfIntroduction.dto;

import lombok.*;

/**
 * 박채원 22.10.03 작성
 */

@Data
@Builder
public class SelfIntroductionDTO {
    private Long selfId;
    private String selfInterActivity;
    private String selfLetter;
    private Long resumeId;
}
