package com.goodjob.admin.apexchart;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 10.6 공고현황 By.OH
 * JSON형식으로 보낼 때 ApexChart가 지원하는 형식을 맞추기 위해 필드명이 x,y
 */

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostStatistics {

    // 진행공고
    private Long x;
    // 마감공고
    private Long y;

}
