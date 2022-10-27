package com.goodjob;

import com.goodjob.company.repository.RegionRepository;
import com.goodjob.company.service.CompanyService;
import com.goodjob.post.Post;
import com.goodjob.post.occupation.service.OccupationService;
import com.goodjob.post.postdto.PageRequestDTO;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.post.postdto.PostDTO;
import com.goodjob.post.postdto.PostMainCardDTO;
import com.goodjob.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Log4j2
public class HomeController {

    private final OccupationService occuService;
    private final CompanyService companyService;
    private final PostService postService;

    @GetMapping("/")
    public String main(){
        return "mainPage";
    }

    @GetMapping("/login")
    public String login() { return "login";}


    @GetMapping("/search")
    public String search(PageRequestDTO pageRequestDTO, Model model) {
        log.info("controller.......search.........."+pageRequestDTO);
        pageRequestDTO.setSize(12);
        PageResultDTO<Post, PostMainCardDTO> result = postService.getListInMain(pageRequestDTO);
        model.addAttribute("occuAll",occuService.searchOccName());
        model.addAttribute("regName",companyService.searchRegName());
        model.addAttribute("salaryRange",postService.searchSalaryRange());
        model.addAttribute("result",result);
        return "searchPage";
    }

}

