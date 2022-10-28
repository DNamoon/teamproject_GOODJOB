package com.goodjob.post.controller;

import com.goodjob.post.fileupload.FileService;
import com.goodjob.post.occupation.service.OccupationService;
import com.goodjob.post.postdto.PostInsertDTO;
import com.goodjob.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostControllerV2 {

    private final PostService postService;
    private final OccupationService occupationService;

    @GetMapping("/save")
    public String postSaveForm(){
        return "/post/postInsertForm";
    }

    @PostMapping("/save")
    public String postSave(PostInsertDTO postInsertDTO) throws IOException {
        log.info("postInsertDTO={}",postInsertDTO);
        postService.savePost(postInsertDTO);
        return "redirect:/";
    }
}
