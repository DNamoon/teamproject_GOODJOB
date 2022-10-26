package com.goodjob.post.service;

import com.goodjob.post.Post;
import com.goodjob.post.postdto.PostBbsDto;
import com.goodjob.post.postdto.PostSaveDto;
import com.goodjob.post.util.EntityDtoMapper;
import com.goodjob.post.util.PageRequestDTO;
import com.goodjob.post.util.PageResultDTO;
import com.goodjob.post.util.ResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PService extends EntityDtoMapper {

    ResultDto<Post, PostBbsDto> getPostList();



//    ResultDto<Post, PostBbsDto> getPostList(PageRequestDTO pageRequestDTO);

    PageResultDTO<Post, PostBbsDto> getPostList(PageRequestDTO pageRequestDTO);

    PageResultDTO<Post, PostBbsDto> getList(PageRequestDTO requestDTO);

    Page<PostBbsDto> findAllPostsByComId(String comLoginId, Pageable pageRequest);

    PostBbsDto getPost(Long postId);


    Long save(PostSaveDto postSaveDto, String comLoginId);
}
