package com.goodjob.post.service;

import com.goodjob.post.EntityDtoMapper;
import com.goodjob.post.Post;
import com.goodjob.post.postdto.PageRequestDTO;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.post.postdto.PostDTO;

import java.text.ParseException;

public interface PostService extends EntityDtoMapper {
    PageResultDTO<Post, PostDTO> getList(PageRequestDTO pageRequestDTO);

    Long register(PostDTO postDTO) throws ParseException;

    PostDTO read(Long postId);

    void remove(Long postId);
}
