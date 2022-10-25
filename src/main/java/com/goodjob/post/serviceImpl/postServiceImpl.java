package com.goodjob.post.serviceImpl;

import com.goodjob.company.Company;
import com.goodjob.company.repository.CompanyRepository;
import com.goodjob.post.Post;
import com.goodjob.post.PostRepository;
import com.goodjob.post.QPost;
import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.repository.OccupationRepository;
import com.goodjob.post.postdto.PageRequestDTO;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.post.postdto.PostDTO;
import com.goodjob.post.service.PostService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
@Log4j2
@Service
public class postServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final OccupationRepository occupationRepository;
    private final CompanyRepository companyRepository;

    @Override
    public PageResultDTO<Post, PostDTO> getList(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("postId").descending());
        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);
        Page<Post> result = postRepository.findAll(booleanBuilder,pageable);
        Function<Post,PostDTO> fn = (this::entityToDto);
        return new PageResultDTO<>(result,fn);

    }
    @Override
    public Long register(PostDTO postDTO) {
        Optional<Occupation> oOcc = occupationRepository.findById(postDTO.getOccId());
        Optional<Company> com = companyRepository.findByComLoginId(postDTO.getComLoginId());
        log.info("service.....register..."+postDTO);
        Post entity = null;
        try {
            entity = dtoToEntity(postDTO, oOcc.get(),com.get());
        } catch (ParseException e) {
            log.info("Date 객체를 변환하는데 에러가 발생했습니다.");
        }
        postRepository.save(entity);
        return entity.getPostId();
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

    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO){
        log.info("service.......getSearch: "+pageRequestDTO);
        QPost qPost = QPost.post;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(pageRequestDTO.getAuthType()!=null){
            if(pageRequestDTO.getAuthType().equals("company")){
                booleanBuilder.and(qPost.postComId.comLoginId.eq(pageRequestDTO.getAuth()));
            }
            // user 추가해야함.
        }



        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();
        BooleanExpression booleanExpression = qPost.postId.gt(0);
        booleanBuilder.and(booleanExpression);



        if(type == null || type.trim().length() == 0 ){
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("t")){
            conditionBuilder.or(qPost.postTitle.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qPost.postComId.comName.contains(keyword));
        }
        if(type.contains("o")){
            conditionBuilder.or(qPost.postOccCode.occName.contains(keyword));
        }
        if(type.contains("r")){
            conditionBuilder.or(qPost.region.contains(keyword));
        }
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
