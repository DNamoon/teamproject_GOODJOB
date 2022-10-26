package com.goodjob.post.service;

import com.goodjob.post.postdto.*;
import com.goodjob.post.util.EntityDtoMapper;
import com.goodjob.post.Post;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface PostService extends EntityDtoMapper {
    PageResultDTO<Post, PostDTO> getList(PageRequestDTO pageRequestDTO);


    PageResultDTO<Post,PostMainCardDTO> getListInMain();

    Long register(PostDTO postDTO) throws ParseException;

    PostDTO read(Long postId);

    void remove(Long postId);

    Long savePost(PostInsertDTO postInsertDTO) throws IOException;
}
