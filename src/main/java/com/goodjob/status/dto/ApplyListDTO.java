package com.goodjob.status.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.sql.Date;

/**
 * 박채원 22.10.26 작성
 */

@Data
@AllArgsConstructor
@Builder
public class ApplyListDTO {
    private int page;
    private int size;

    private Long statId;
    private Long statPostId;
    private Long statResumeId;
    private Date statApplyDate;
    private String postName;
    private String companyName;
    private String resumeTitle;

    public ApplyListDTO(){
        this.page=1;
        this.size=10;
    }
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1,size, sort);
    }
}
