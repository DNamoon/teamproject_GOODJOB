package com.goodjob.post.controller;

import com.goodjob.post.postdto.PostBbsDto;
import com.goodjob.post.service.PService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/p")
@RequiredArgsConstructor
public class PRestController {
    private final PService pService;

    @GetMapping(value = {"/postListR"})
    @ResponseBody
    public ResponseEntity<List<PostBbsDto>> getPostListR(){

        return new ResponseEntity<>(pService.getPostList().getDtoList(), HttpStatus.OK);
    }

    @RequestMapping(value = {"/bbsR"}, method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ResponseEntity<Page<PostBbsDto>> getPostListR(HttpServletRequest httpServletRequest, @RequestParam(defaultValue = "0") int curPage, @RequestParam(defaultValue = "10") int numPerPage){
        HttpSession httpSession = httpServletRequest.getSession();
        Pageable pageable = PageRequest.of(curPage, numPerPage, Sort.by(Sort.Direction.DESC,"postId"));
        Page<PostBbsDto> pageDto = pService.findAllPostsByComId(httpSession.getAttribute("sessionId").toString(),pageable);
        if(!pageDto.isEmpty()){

            System.out.println(httpSession);
            System.out.println(pageable);
            System.out.println(pageDto);

            return new ResponseEntity<>(pageDto, HttpStatus.OK);
        } else {
            throw new Error("Error: invalid page request(NO CONTENTS)");
        }
    }
}
