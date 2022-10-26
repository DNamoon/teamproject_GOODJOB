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

    private String type; // 리스트에서 검색 조건(ex: title, company,occupation, titleCompanyName)
    private String keyword; // 리스트 검색어
    private String sort; // 리스트 순서(최신순(new), 인기순(count), 연봉순(salary), 종료직전(end))
    private String filter; // 연봉 선택 시

    public PageRequestDTO(){
        this.page=1;
        this.size=10;
    }
    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1,size, sort);
    }

}
