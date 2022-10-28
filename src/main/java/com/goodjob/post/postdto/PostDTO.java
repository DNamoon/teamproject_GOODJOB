package com.goodjob.post.postdto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String recruitNum;
    private String startDate;
    private String endDate;
    private String gender;
    private String regionId;
    private String regionName;
    private Long salaryId;
    private String salaryRange;
    private int count;
    private int remainDay;

    private Long occId;
    private String occName;
    private String comLoginId;
    private String comName;


}
