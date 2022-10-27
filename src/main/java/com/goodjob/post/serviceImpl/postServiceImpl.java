package com.goodjob.post.serviceImpl;

import com.goodjob.company.Company;
import com.goodjob.company.Region;
import com.goodjob.company.repository.CompanyRepository;
import com.goodjob.company.repository.RegionRepository;
import com.goodjob.post.Post;
import com.goodjob.post.fileupload.FileService;
import com.goodjob.post.fileupload.UploadFile;
import com.goodjob.post.postdto.*;
import com.goodjob.post.repository.PostRepository;
import com.goodjob.post.QPost;
import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.repository.OccupationRepository;
import com.goodjob.post.service.PostService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
@Log4j2
@Service
public class postServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final OccupationRepository occupationRepository;
    private final CompanyRepository companyRepository;
    private final RegionRepository regionRepository;
    private final FileService fileService;

    @Override
    public PageResultDTO<Post, PostDTO> getList(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("postId").descending());
        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);
        Page<Post> result = postRepository.findAll(booleanBuilder,pageable);
        Function<Post,PostDTO> fn = (this::entityToDto);
        return new PageResultDTO<>(result,fn);

    }
    @Override
    public PageResultDTO<Post,PostMainCardDTO> getListInMain(){

        Pageable pageable = PageRequest.of(0,8,Sort.by(Sort.Direction.DESC, "postId"));
//        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);
        Page<Post> result = postRepository.findAll(pageable);
        Function<Post,PostMainCardDTO> fn = (this::entityToDtoInMain);
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public Long register(PostDTO postDTO) {
        Optional<Occupation> oOcc = occupationRepository.findById(postDTO.getOccId());
        Optional<Company> com = companyRepository.findByComLoginId(postDTO.getComLoginId());
        Optional<Region> reg = regionRepository.findById(postDTO.getRegionId());
        log.info("service.....register..."+postDTO);
        Post entity = null;
        if(oOcc.isPresent() && com.isPresent() && reg.isPresent()){
            try {
                entity = dtoToEntity(postDTO, oOcc.get(),com.get(),reg.get());
                postRepository.save(entity);
                return entity.getPostId();
            } catch (ParseException e) {
                log.info("Date 객체를 변환하는데 에러가 발생했습니다.");
            }
        }
        return null;
    }

    @Override
    public PostDTO read(Long postId){
        Optional<Post> result = postRepository.findById(postId);
        return result.map(this::entityToDto).orElse(null);
    }
    @Override
    public void remove(Long postId){
        postRepository.deleteById(postId);
    }

    @Override
    public Long savePost(PostInsertDTO postInsertDTO) throws IOException {
        Optional<Occupation> occupation = occupationRepository.findById(postInsertDTO.getPostOccCode());
        Optional<Company> company = companyRepository.findByComLoginId(postInsertDTO.getComLoginId());
        Optional<Region> region = regionRepository.findById(postInsertDTO.getPostRegion());
        List<UploadFile> uploadFiles = fileService.storeFiles(postInsertDTO.getPostImg());
        log.info("service.....register..."+postInsertDTO);
        if(occupation.isPresent() && company.isPresent() && region.isPresent()){
            Post post = new Post(postInsertDTO.getPostTitle(), occupation.get(), company.get(),
                    postInsertDTO.getPostContent(), postInsertDTO.getPostRecruitNum(),
                    postInsertDTO.getPostStartDate(), postInsertDTO.getPostEndDate(),
                    postInsertDTO.getPostGender(),region.get(),uploadFiles,postInsertDTO.getSalary());
            postRepository.save(post);
            return post.getPostId();
        }
        return null;
    }

    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO){
        log.info("service.......getSearch: "+pageRequestDTO);
        QPost qPost = QPost.post;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 기업, 유저가 자신이 작성한 글만 보게하는 코드
        if(pageRequestDTO.getAuthType()!=null){
            if(pageRequestDTO.getAuthType().equals("company")){
                booleanBuilder.and(qPost.postComId.comLoginId.eq(pageRequestDTO.getAuth()));
            }
            // user 추가해야함.
        }
        // 기업, 유저 필터 코드 끝.


        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();
        BooleanExpression booleanExpression = qPost.postId.gt(0);
        booleanBuilder.and(booleanExpression);


        // 검색 조건 처리 코드
        if(type == null || type.trim().length() == 0 ){
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("title")){
            conditionBuilder.or(qPost.postTitle.contains(keyword));
        }
        if(type.contains("company")){
            conditionBuilder.or(qPost.postComId.comName.contains(keyword));
        }
        if(type.contains("occupation")){
            conditionBuilder.or(qPost.postOccCode.occName.contains(keyword));
        }
        if(type.contains("region")){
            conditionBuilder.or(qPost.postRegion.regName.contains(keyword));
        }
        if(type.contains("titleCompanyName")){
            conditionBuilder.or(qPost.postTitle.contains(keyword)).or(qPost.postComId.comName.contains(keyword));
        }
        booleanBuilder.and(conditionBuilder);
        // 검색 조건 처리 코드 끝

        return booleanBuilder;
    }
}
