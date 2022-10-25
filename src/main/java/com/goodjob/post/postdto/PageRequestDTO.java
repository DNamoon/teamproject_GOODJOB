package com.goodjob.post.postdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;

    private String authType; // com, user
    private String auth; // 기업,유저 sessionId

    private String type; // 리스트에서 검색 조건
    private String keyword; // 리스트 검색어

    public PageRequestDTO(){
        this.page=1;
        this.size=10;
    }
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1,size, sort);
    }

}
