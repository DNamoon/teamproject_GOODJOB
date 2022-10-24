package com.goodjob.post.serviceImpl;

import com.goodjob.company.Company;
import com.goodjob.company.repository.CompanyRepository;
import com.goodjob.post.Post;
import com.goodjob.post.PostRepository;
import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.repository.OccupationRepository;
import com.goodjob.post.postdto.PostBbsDto;
import com.goodjob.post.postdto.PostSaveDto;
import com.goodjob.post.service.PService;
import com.goodjob.post.util.PageRequestDTO;
import com.goodjob.post.util.PageResultDTO;
import com.goodjob.post.util.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class PServiceImpl implements PService {
    private final PostRepository postRepository;
    private final OccupationRepository occupationRepository;
    private final CompanyRepository companyRepository;


    @Override
    public ResultDto<Post, PostBbsDto> getPostList() {
        return null;
    }

    @Override
    public PageResultDTO<Post, PostBbsDto> getPostList(PageRequestDTO pageRequestDTO) {

        Sort sort = Sort.by("postId").descending();
        Pageable pageable = pageRequestDTO.getPageable(sort);
        Page<Post> result = postRepository.findAll(pageable);

        Function<Post, PostBbsDto> fn = this::entityToDto;

//        List<Post> result = postRepository.findAll();
//        Function<Post, PostBbsDto> fn = (post -> entityToDto(post));

        return new PageResultDTO<>(result, fn);

    }

    @Override
    public PageResultDTO<Post, PostBbsDto> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("postId").descending());
        Page<Post> result = postRepository.findAll(pageable);
        Function<Post,PostBbsDto> fn = (this::entityToDto);
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public Page<PostBbsDto> findAllPostsByComId(String comLoginId,Pageable pageRequest){
        //    @PageableDefault 넣어보자.
        Optional<Company> oCom = companyRepository.findByComLoginId(comLoginId);
        Page<PostBbsDto> pageDto;
        if(oCom.isPresent()){
            pageDto = postRepository.findAllByPostComId(oCom.get(),pageRequest).map(this::entityToDto);
            return pageDto;
        }
        return null;
    }
    @Override
    public PostBbsDto getPost(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        return post.map(this::entityToDto).orElse(null);
    }

    @Override
    public Long save(PostSaveDto postSaveDto, String comLoginId){
        Optional<Occupation> occ = occupationRepository.findById(postSaveDto.getOccId());
        Optional<Company> com = companyRepository.findByComLoginId(comLoginId);
        if(occ.isPresent() && com.isPresent()){
            return postRepository.save(dtoToEntity(postSaveDto,occ.get(),com.get())).getPostId();
        }
        return 0L;
    }
}
