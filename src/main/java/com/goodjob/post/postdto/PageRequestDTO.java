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

    private int page; // 요청하는 현재 페이지 번호. 기본 1
//    private int size; // 페이지 당 게시글 숫자. 기본 10
    private int size; // 페이지 당 게시글 숫자. 기본 10
    // "company"은 session 에서 Type 값이 company 인 경우
    // "user"은 session 에서 Type 값이 user 인 경우
    private String authType;
    // session 에서 sessionId 값으로 userLoginId, comLoginId db값과 같다.
    private String auth;
    // "title"은 제목 검색
    // "company"은 회사명 검색
    // "occupation"은 직종명 검색
    // "titleCompanyName"은 제목 또는 회사명 검색
    private String type;
    private String keyword; // 리스트 검색어
    // "new"(기본)은 최신순
    // "count"은 인기순(조회수)
    // "salary"는 연봉 높은 순
    // "end" 종료 날짜가 가까운 순
    private String sort;
    // 직종, 연봉, 지역 추가 필터링 조건
    private String filterOccupation;
//    private String filterRegion;
    private String filterAddress;
    private String filterSalary;
    // "active"(기본)은 현재 모집중인 공고 리스트만 가져오는 조건을 추가한다.
    // "beforeStart"은 모집이 아직 시작하지 않은 리스트만 가져오는 조건을 추가한다.
    // "afterEnd"은 모집이 종료된 공고 리스트만 가져오는 조건을 추가한다.
    // "beforeAfter"은 모집이 시작되지 않거나 종료된 공고 리스트만 가져오는 조건을 추가한다.
    // "all"은 모집이 시작되지 않거나 모집 중이거나 종료된, 즉, 모든 공고 리스트를 가져오는 조건을 추가한다.
    private String outOfDateState;

    public PageRequestDTO(){
        this.page=1;
        this.size=10;
        this.sort="new";
        this.outOfDateState="active";
        this.type="titleCompanyName";
    }
    public Pageable getPageable(Sort sort){

        return PageRequest.of(page-1,size, sort);
    }

}
