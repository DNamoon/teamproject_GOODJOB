package com.goodjob.post.serviceImpl;

import com.goodjob.company.Company;
import com.goodjob.company.Region;
import com.goodjob.company.repository.CompanyRepository;
import com.goodjob.company.repository.RegionRepository;
import com.goodjob.post.Post;
import com.goodjob.post.postdto.PostMainCardDTO;
import com.goodjob.post.repository.PostRepository;
import com.goodjob.post.QPost;
import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.repository.OccupationRepository;
import com.goodjob.post.postdto.PageRequestDTO;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.post.postdto.PostDTO;
import com.goodjob.post.salary.Salary;
import com.goodjob.post.salary.SalaryRepository;
import com.goodjob.post.service.PostService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@Service
public class postServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final OccupationRepository occupationRepository;
    private final CompanyRepository companyRepository;
    private final SalaryRepository salaryRepository;
    private final RegionRepository regionRepository;

    @Override
    public PageResultDTO<Post, PostDTO> getList(PageRequestDTO pageRequestDTO){
        log.info("service......getList..."+pageRequestDTO);
        Pageable pageable = pageRequestDTO.getPageable(decideSort(pageRequestDTO));
        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);
        Page<Post> result = postRepository.findAll(booleanBuilder,pageable);
        Function<Post,PostDTO> fn = (this::entityToDto);
        return new PageResultDTO<>(result,fn);

    }

    @Override
    public PageResultDTO<Post,PostMainCardDTO> getListInMain(PageRequestDTO pageRequestDTO){
        log.info("service......getList..."+pageRequestDTO);
        Pageable pageable = pageRequestDTO.getPageable(decideSort(pageRequestDTO));
        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);
        Page<Post> result = postRepository.findAll(booleanBuilder,pageable);
        Function<Post,PostMainCardDTO> fn = (this::entityToDtoInMain);
        return new PageResultDTO<>(result,fn);
    }
    // pageRequestDTO 의 sort 값에 따라 공고 리스트의 정렬에 필요한 Sort를 리턴해주는 메솓즈
    // getList()와 getListInMain() 에 사용한다.
    private Sort decideSort(PageRequestDTO pageRequestDTO){
        switch (pageRequestDTO.getSort()) {
            case "new":
                return Sort.by("postId").descending();
            case "count":
                return Sort.by("count").descending();
            case "salary":
                return Sort.by("salary").descending();
            case "end":
                return Sort.by("postEndDate").ascending();
        }
        return  null;
    }

    @Override
    public Long register(PostDTO postDTO) {
        Optional<Occupation> oOcc = occupationRepository.findById(postDTO.getOccId());
        Optional<Company> com = companyRepository.findByComLoginId(postDTO.getComLoginId());
        Optional<Region> reg = regionRepository.findById(postDTO.getRegionId());
        Optional<Salary> sal = salaryRepository.findById(postDTO.getSalaryId());
        log.info("service.....register..."+postDTO);
        Post entity = null;
        if(oOcc.isPresent() && com.isPresent() && reg.isPresent() && sal.isPresent()){
            try {
                entity = dtoToEntity(postDTO, oOcc.get(),com.get(),reg.get(),sal.get());
                postRepository.save(entity);
                return entity.getPostId();
            } catch (ParseException e) {
                log.info("Date 객체를 변환하는데 에러가 발생했습니다.");
            }
        }
        return null;
    }

    @Override
    @Transactional
    public PostDTO read(Long postId){
        Optional<Post> result = postRepository.findById(postId);
        // 게시글 조회 후 조회수를 +1 한다.
        postRepository.increasePostCount(postId);
        return result.map(this::entityToDto).orElse(null);
    }
    @Override
    public void remove(Long postId){
        postRepository.deleteById(postId);
    }

    // 공고 리스트를 PageRequestDTO의 sort 값에 따라 정렬을 다르게하는 조건을 리턴해주는 메소드.
    // getSearch() 메소드에 사용된다.
    private BooleanBuilder getOutOfDateState(PageRequestDTO pageRequestDTO,QPost qPost){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        LocalDate curDate = LocalDate.now();
        // "모집 시작일이 현재 보다 앞이다."라는 조건 즉, 모집 중.
        BooleanExpression startDateBeforeNow = qPost.postStartDate.before(java.sql.Date.valueOf(curDate));
        // "모집 종료일이 현재 보다 뒤다"라는 조건. 즉, 모집 중.
        BooleanExpression endDateAfterNow = qPost.postEndDate.after(java.sql.Date.valueOf(curDate));
        // "모집 시작일이 현재 보다 뒤다"라는 조건. 즉, 모집 시작 전.
        BooleanExpression startDateAfterNow = qPost.postStartDate.after(java.sql.Date.valueOf(curDate));
        // "모집 종료일이 현재 보다 앞다"라는 조건. 즉, 모집 종료.
        BooleanExpression endDateBeforeNow = qPost.postEndDate.before(java.sql.Date.valueOf(curDate));
        switch (pageRequestDTO.getOutOfDateState()) {
            case "active":
                return booleanBuilder.and(startDateBeforeNow).and(endDateAfterNow);
            case "beforeStart":
                return booleanBuilder.and(startDateAfterNow);
            case "afterEnd":
                return booleanBuilder.and(endDateBeforeNow);
            case "beforeAfter":
                return booleanBuilder.and(startDateAfterNow.or(endDateBeforeNow));
            case "all":
                return booleanBuilder;
        }
        return null;
    }

    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO){
        log.info("service.......getSearch: "+pageRequestDTO);



        QPost qPost = QPost.post;
        BooleanBuilder booleanBuilder = new BooleanBuilder();




        // 세션의 authType = "company" 이라면 로그인한 기업회원의 글만
        // 가져오는 조건 추가.
        if(pageRequestDTO.getAuthType()!=null){
            if(pageRequestDTO.getAuthType().equals("company")){
                booleanBuilder.and(qPost.postComId.comLoginId.eq(pageRequestDTO.getAuth()));
            }
        }
        // 기업 필터 코드 끝.

        // 반드시 postId가 0보다 커야하는 조건을 추가한다.
        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();
        BooleanExpression booleanExpression = qPost.postId.gt(0);
        //


        // (모집중, 모집시작 전, 모집 종료, 전체) 중의 하나의 조건을 추가하는 코드
        booleanBuilder.and(booleanExpression).and(getOutOfDateState(pageRequestDTO,qPost));


        // 검색 조건 처리 코드
        if(type == null || type.trim().length() == 0 ){
            return booleanBuilder;
        }
        // 검색 조건으로 type = "title" 인 경우, 공고 제목에 keyword 를 가지는 글만
        // 가져오는 조건 추가
        BooleanBuilder booleanBuilderWithSearch = new BooleanBuilder();
        if(type.contains("title")){
            booleanBuilderWithSearch.or(qPost.postTitle.contains(keyword));
        }
        // 검색 조건으로 type = "company" 인 경우, 기업명으로 keyword 를 가지는 글만
        // 가져오는 조건 추가
        if(type.contains("company")){
            booleanBuilderWithSearch.or(qPost.postComId.comName.contains(keyword));
        }
        // 검색 조건으로 type = "occupation" 인 경우, 직종명으로 keyword 를 가지는 글만
        // 가져오는 조건 추가
        if(type.contains("occupation")){
            booleanBuilderWithSearch.or(qPost.postOccCode.occName.contains(keyword));
        }
        // 검색 조건으로 type = "region" 인 경우, 지역명으로 keyword 를 가지는 글만
        // 가져오는 조건 추가
        if(type.contains("region")){
            booleanBuilderWithSearch.or(qPost.postRegion.regName.contains(keyword));
        }
        // 검색 조건으로 type = "titleCompanyName" 인 경우, 공고 제목 또는 회사명으로
        // keyword 를 가지는 글만 가져오는 조건 추가
        if(type.contains("titleCompanyName")){
            booleanBuilderWithSearch.or(qPost.postTitle.contains(keyword)).or(qPost.postComId.comName.contains(keyword));
        }
        log.info("=======================================1"+booleanBuilderWithSearch);
//        if(booleanBuilderWithSearch!=null){
            booleanBuilder.and(booleanBuilderWithSearch);
//        }

        // 검색 조건 처리 코드 끝

        BooleanBuilder booleanBuilderWithFilter = new BooleanBuilder();
        if(pageRequestDTO.getFilterOccupation()!=null){
            if (!(pageRequestDTO.getFilterOccupation().isEmpty() || pageRequestDTO.getFilterOccupation().trim().length() == 0)){
                booleanBuilderWithFilter.and(qPost.postOccCode.occName.eq(pageRequestDTO.getFilterOccupation()));
            }
            if (!(pageRequestDTO.getFilterRegion().isEmpty() || pageRequestDTO.getFilterRegion().trim().length() == 0)){
                booleanBuilderWithFilter.and(qPost.postRegion.regName.eq(pageRequestDTO.getFilterRegion()));
            }
            if (!(pageRequestDTO.getFilterSalary().isEmpty() || pageRequestDTO.getFilterSalary().trim().length() == 0)){
                booleanBuilderWithFilter.and(qPost.salary.salaryRange.eq(pageRequestDTO.getFilterSalary()));
            }
            booleanBuilder.and(booleanBuilderWithFilter);

        }

        return booleanBuilder;
    }

    @Override
    public List<String> searchSalaryRange(){
        return  postRepository.salaryRange();
    }
}
