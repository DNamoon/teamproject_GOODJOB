package com.goodjob.post.service;
import com.goodjob.post.Post;
import com.goodjob.post.postdto.PageRequestDTO;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.post.postdto.PostDTO;
import com.goodjob.post.postdto.PostMainCardDTO;
import com.goodjob.post.util.EntityDtoMapper;

import java.text.ParseException;
import java.util.List;


public interface PostService extends EntityDtoMapper {
    PageResultDTO<Post, PostDTO> getList(PageRequestDTO pageRequestDTO);




    PageResultDTO<Post,PostMainCardDTO> getListInMain();

    Long register(PostDTO postDTO) throws ParseException;

    PostDTO read(Long postId);

    void remove(Long postId);

    List<String> searchSalaryRange();
}
