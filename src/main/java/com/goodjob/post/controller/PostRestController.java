package com.goodjob.post.controller;

import com.goodjob.post.Post;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.post.postdto.PostMainCardDTO;
import com.goodjob.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/post"})
@Log4j2
@RequiredArgsConstructor
public class PostRestController {
    private final PostService postService;

    @GetMapping(value = {"/listInMain"})
    public ResponseEntity<PageResultDTO<Post,PostMainCardDTO>> getListInMain(){
        PageResultDTO<Post,PostMainCardDTO>  result = postService.getListInMain();
        log.info("Controller.............getListInMain...."+result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
