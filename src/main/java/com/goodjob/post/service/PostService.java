package com.goodjob.post.service;
import com.goodjob.company.Region;
import com.goodjob.post.Post;
import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.postdto.*;
import com.goodjob.post.salary.PostSalary;
import com.goodjob.post.util.EntityDtoMapper;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


public interface PostService extends EntityDtoMapper {
    PageResultDTO<Post, PostDTO> getList(PageRequestDTO pageRequestDTO);





    PageResultDTO<Post, PostCardDTO> getPagingPostList(PageRequestDTO pageRequestDTO);

    Long register(PostDTO postDTO) throws ParseException;

    List<Occupation> getListOccupation();

    List<Region> getListRegion();

    List<PostSalary> getListSalary();

    Long savePost(PostInsertDTO postInsertDTO) throws IOException;

    PostDTO read(Long postId);

    void remove(Long postId);

    List<String> searchSalaryRange();
}
