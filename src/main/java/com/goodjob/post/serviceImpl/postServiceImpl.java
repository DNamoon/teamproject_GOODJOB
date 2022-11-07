package com.goodjob.post.serviceImpl;

import com.goodjob.company.Company;
import com.goodjob.company.Region;
import com.goodjob.company.repository.CompanyRepository;
import com.goodjob.company.repository.RegionRepository;
import com.goodjob.post.Address;
import com.goodjob.post.Post;
import com.goodjob.post.fileupload.FileService;
import com.goodjob.post.fileupload.UploadFile;
import com.goodjob.post.postdto.*;
import com.goodjob.post.repository.PostRepository;
import com.goodjob.post.QPost;
import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.repository.OccupationRepository;
import com.goodjob.post.salary.PostSalary;
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

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.function.Function;

@RequiredArgsConstructor
@Log4j2
@Service
public class postServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final OccupationRepository occupationRepository;
    private final CompanyRepository companyRepository;
    private final SalaryRepository salaryRepository;
    private final RegionRepository regionRepository;
    private final FileService fileService;

    @Override
    public PageResultDTO<Post, PostCardDTO> getPagingPostList(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable(decideSort(pageRequestDTO));
        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);
        Page<Post> result = postRepository.findAll(booleanBuilder,pageable);
        Function<Post, PostCardDTO> fn = (this::entityToDtoInMain);
        return new PageResultDTO<>(result,fn);
    }
    @Override
    public PageResultDTO<Post, PostComMyPageDTO> getPagingPostListInComMyPage(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable(decideSort(pageRequestDTO));
        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);
        Page<Post> result = postRepository.findAll(booleanBuilder,pageable);
        Function<Post, PostComMyPageDTO> fn = (this::entityToDtoInComMyPage);
        return new PageResultDTO<>(result,fn);
    }

    // 세션 정보를 사용하여 company에서 필요한 정보를 모아 DTO 생성
    @Override
    public CompanyInfoDTO getComInfo(String sessionId){

        Optional<Company> optionalCompany = companyRepository.findByComLoginId(sessionId); // 옵셔널 company 를 가져온다.

        if(optionalCompany.isPresent()){
            Company company = optionalCompany.get();
            ArrayList<String> list = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(company.getComAddress(),"@");
            while(st.hasMoreTokens()){
                list.add(st.nextToken());
            }
            return new CompanyInfoDTO(list.get(0), list.get(1), list.get(2), list.get(3), company.getComName(),company.getComBusiNum(),company.getComComdivCode().getComdivName());
        }
        return null;
    }
    @Override
    public List<Occupation> getListOccupation(){
        return occupationRepository.findAll();
    }
    @Override
    public List<Region> getListRegion(){
        return regionRepository.findAll();
    }
    @Override
    public List<PostSalary> getListSalary(){
        return salaryRepository.findAll();
    }

    @Override
    public Long savePost(PostInsertDTO postInsertDTO) throws IOException {
        Address address = new Address(postInsertDTO.getPostcode(), postInsertDTO.getPostAddress(), postInsertDTO.getPostDetailAddress(),postInsertDTO.getEtc());
        Optional<Occupation> occupation = occupationRepository.findById(postInsertDTO.getPostOccCode());
        Optional<Company> company = companyRepository.findByComLoginId(postInsertDTO.getComLoginId());
        List<UploadFile> uploadFiles = fileService.storeFiles(postInsertDTO.getPostImg());
        Optional<PostSalary> salary = salaryRepository.findById(postInsertDTO.getPostSalaryId());
        if(occupation.isPresent() && company.isPresent() && salary.isPresent()){
            Post post = postRepository.save(dtoToEntityForInsert(postInsertDTO,occupation.get(),company.get(),salary.get(),uploadFiles,address));
            return post.getPostId();
        }
        return null;
    }
    @Override
    public PostDetailsDTO readPost(Long postId){
        Optional<Post> result = postRepository.findById(postId);
        // 게시글 조회 후 조회수를 +1 한다.
        postRepository.increasePostCount(postId);
        return result.map(this::entityToDtoForRead).orElse(null);
    }
    @Override
    public PostInsertDTO getPostById(Long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        return optionalPost.map(this::entityToDtoForUpdate).orElse(null);
    }
    @Override
    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }

    // pageRequestDTO 의 sort 값에 따라 공고 리스트의 정렬에 필요한 Sort를 리턴해주는 메솓즈
    // getList()와 getListInMain() 에 사용한다.
    private Sort decideSort(PageRequestDTO pageRequestDTO){
        switch (pageRequestDTO.getSort()) {
            case "new":
                return Sort.by("postId").descending();
            case "count":
                return Sort.by("postReadCount").descending();
            case "salary":
                return Sort.by("postSalary").descending();
            case "end":
                return Sort.by("postEndDate").ascending();
        }
        return  null;
    }

    // 공고 리스트를 PageRequestDTO의 sort 값에 따라 정렬을 다르게하는 조건을 리턴해주는 메소드.
    // getSearch() 메소드에 사용된다.
    private BooleanBuilder getOutOfDateState(PageRequestDTO pageRequestDTO,QPost qPost){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        LocalDate curDateEnd = LocalDate.now().minusDays(1);
        LocalDate curDateStart = LocalDate.now().plusDays(1);
        // "모집 시작일이 현재 보다 앞이다."라는 조건 즉, 모집 중.
        BooleanExpression startDateBeforeNow = qPost.postStartDate.before(java.sql.Date.valueOf(curDateStart));
        // "모집 종료일이 현재 보다 뒤다"라는 조건. 즉, 모집 중.
        BooleanExpression endDateAfterNow = qPost.postEndDate.after(java.sql.Date.valueOf(curDateEnd));
        // "모집 시작일이 현재 보다 뒤다"라는 조건. 즉, 모집 시작 전.
        BooleanExpression startDateAfterNow = qPost.postStartDate.after(java.sql.Date.valueOf(curDateStart));
        // "모집 종료일이 현재 보다 앞다"라는 조건. 즉, 모집 종료.
        BooleanExpression endDateBeforeNow = qPost.postEndDate.before(java.sql.Date.valueOf(curDateEnd));
        switch (pageRequestDTO.getOutOfDateState()) {
            case "active":
                booleanBuilder.and(startDateBeforeNow).and(endDateAfterNow);
                break;
            case "beforeStart":
                booleanBuilder.and(startDateAfterNow);
                break;
            case "afterEnd":
                booleanBuilder.and(endDateBeforeNow);
                break;
            case "beforeAfter":
                booleanBuilder.and(startDateAfterNow.or(endDateBeforeNow));
                break;
            case "all":
                break;
        }
        return null;
    }

    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO){
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



        // ==================         직종, 주소, 연봉 필터링 ============================
        BooleanBuilder booleanBuilderWithFilter = new BooleanBuilder();
        if (!(pageRequestDTO.getFilterOccupation()==null||pageRequestDTO.getFilterOccupation().isEmpty())){
            booleanBuilderWithFilter.and(qPost.postOccCode.occName.eq(pageRequestDTO.getFilterOccupation()));
        }
//        if (!(pageRequestDTO.getFilterRegion()==null||pageRequestDTO.getFilterRegion().isEmpty())){
//            booleanBuilderWithFilter.and(qPost.postRegion.regName.eq(pageRequestDTO.getFilterRegion()));
//        }
        if (!(pageRequestDTO.getFilterAddress()==null||pageRequestDTO.getFilterAddress().isEmpty())){
            booleanBuilderWithFilter.and(qPost.address.address1.contains(pageRequestDTO.getFilterAddress()));
        }
        if (!(pageRequestDTO.getFilterSalary()==null||pageRequestDTO.getFilterSalary().isEmpty())){
            booleanBuilderWithFilter.and(qPost.postSalary.salaryRange.eq(pageRequestDTO.getFilterSalary()));
        }
        booleanBuilder.and(booleanBuilderWithFilter);
        // ==================         직종, 주소, 연봉 필터링 ============================

        // 검색 조건 처리 코드
        if((type == null || type.trim().length() == 0 )){
            return booleanBuilder;
        } else if(!(keyword == null || keyword.isEmpty())) {
            BooleanBuilder booleanBuilderWithSearch = new BooleanBuilder();
            switch (type) {
                case "title":
                    // 검색 조건으로 type = "title" 인 경우, 공고 제목에 keyword 를 가지는 글만
                    // 가져오는 조건 추가
                    booleanBuilderWithSearch.or(qPost.postTitle.contains(keyword));
                    break;
                case "company":
                    // 검색 조건으로 type = "company" 인 경우, 기업명으로 keyword 를 가지는 글만
                    // 가져오는 조건 추가
                    booleanBuilderWithSearch.or(qPost.postComId.comName.contains(keyword));
                    break;
                case "occupation":
                    // 검색 조건으로 type = "occupation" 인 경우, 직종명으로 keyword 를 가지는 글만
                    // 가져오는 조건 추가
                    booleanBuilderWithSearch.or(qPost.postOccCode.occName.contains(keyword));
                    break;
                case "region":
                    // 검색 조건으로 type = "region" 인 경우, 지역명으로 keyword 를 가지는 글만
                    // 가져오는 조건 추가
                    booleanBuilderWithSearch.or(qPost.postRegion.regName.contains(keyword));
                    break;
                case "titleCompanyName":
                    // 검색 조건으로 type = "titleCompanyName" 인 경우, 공고 제목 또는 회사명으로
                    // keyword 를 가지는 글만 가져오는 조건 추가
                    booleanBuilderWithSearch.or(qPost.postTitle.contains(keyword)).or(qPost.postComId.comName.contains(keyword));
                    break;
            }
            booleanBuilder.and(booleanBuilderWithSearch);
        }

        // 검색 조건 처리 코드 끝

        System.out.println("불리언 빌더============최종 쿼리 조건 : "+booleanBuilder);
        return booleanBuilder;
    }

    @Override
    public List<String> searchSalaryRange(){
        return  postRepository.salaryRange();
    }

    @Override
    public Optional<Post> findOne(Long postId) {
        return postRepository.findById(postId);
    }
}
