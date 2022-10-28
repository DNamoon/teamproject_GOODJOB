package com.goodjob.post.service;

import com.goodjob.post.postdto.*;
import com.goodjob.post.util.EntityDtoMapper;
import com.goodjob.post.Post;

import com.goodjob.post.postdto.PageRequestDTO;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.post.postdto.PostDTO;
import com.goodjob.post.postdto.PostMainCardDTO;

import java.io.IOException;
import java.text.ParseException;

public interface PostService extends EntityDtoMapper {
    PageResultDTO<Post, PostDTO> getList(PageRequestDTO pageRequestDTO);





    PageResultDTO<Post,PostMainCardDTO> getListInMain(PageRequestDTO pageRequestDTO);

    Long register(PostDTO postDTO) throws ParseException;

    PostDTO read(Long postId);

    void remove(Long postId);

    Long savePost(PostInsertDTO postInsertDTO) throws IOException;

    List<String> searchSalaryRange();
}
