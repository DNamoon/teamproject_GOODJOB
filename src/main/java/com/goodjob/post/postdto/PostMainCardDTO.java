package com.goodjob.post.postdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PostMainCardDTO {

    private Long id;
    private String title;
//    private String startDate;
//    private String endDate;
    private String remainDay;
    private String regionName;

    private String occName;
    private String comName;


}
