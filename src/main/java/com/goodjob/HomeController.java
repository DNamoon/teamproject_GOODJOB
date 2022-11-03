package com.goodjob;

import com.goodjob.company.service.CompanyService;
import com.goodjob.post.Post;
import com.goodjob.post.occupation.service.OccupationService;
import com.goodjob.post.postdto.PageRequestDTO;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.post.postdto.PostCardDTO;
import com.goodjob.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Controller
@RequiredArgsConstructor
@Log4j2
public class HomeController {

    private final OccupationService occuService;
    private final CompanyService companyService;
    private final PostService postService;


    @GetMapping("/login")
    public String login() { return "login";}


    @GetMapping("/search")
    public String search(PageRequestDTO pageRequestDTO, Model model) {
        pageRequestDTO.setSize(12);
        PageResultDTO<Post, PostCardDTO> result = postService.getPagingPostList(pageRequestDTO);
        model.addAttribute("occuAll",occuService.searchOccName());
        model.addAttribute("salaryRange",postService.searchSalaryRange());
        model.addAttribute("result",result);
        log.info(result);
        return "searchPage";
    }
    @GetMapping(value = {"/"})
    public String main(PageRequestDTO pageRequestDTO, Model model){
        pageRequestDTO.setSize(8);
        PageResultDTO<Post, PostCardDTO> result = postService.getPagingPostList(pageRequestDTO);
        model.addAttribute("result",result);
        return "mainPage";
    }

    // mainPage.html 에서 인기, 최신, 연봉, 마감직전 순으로 정렬 클릭시 ajax로
    // 데이터를 보내주는 메소드
    @PostMapping(value = {"/getPagingPostList"})
    @ResponseBody
    public ResponseEntity<PageResultDTO<Post, PostCardDTO>> getPagingPostList(@RequestBody PageRequestDTO pageRequestDTO) {
        log.info("================================="+pageRequestDTO);
        PageResultDTO<Post, PostCardDTO> result = postService.getPagingPostList(pageRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}

