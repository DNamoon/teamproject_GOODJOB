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
    PageResultDTO<Post, PostCardDTO> getPagingPostList(PageRequestDTO pageRequestDTO);

    PageResultDTO<Post, PostComMyPageDTO> getPagingPostListInComMyPage(PageRequestDTO pageRequestDTO);


    CompanyInfoDTO getComInfo(String sessionId);

    List<Occupation> getListOccupation();

    List<Region> getListRegion();

    List<PostSalary> getListSalary();

    Long savePost(PostInsertDTO postInsertDTO) throws IOException;


    PostDetailsDTO readPost(Long postId);

    PostInsertDTO getPostById(Long postId);

    void deletePost(Long postId);

    List<String> searchSalaryRange();
}
