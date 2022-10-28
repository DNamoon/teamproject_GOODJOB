package com.goodjob.post.controller;

import com.goodjob.company.Company;
import com.goodjob.company.service.CompanyService;
import com.goodjob.post.Post;
import com.goodjob.post.occupation.service.OccupationService;
import com.goodjob.post.postdto.PageRequestDTO;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.post.postdto.PostDTO;
import com.goodjob.post.postdto.PostInsertDTO;
import com.goodjob.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping(value = {"/post"})
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final OccupationService occupationService;
    private final CompanyService companyService;



    //---------------------
    @GetMapping("/savePost")
    public String postSaveForm(HttpServletRequest httpServletRequest, Model model){
        String sessionId = getSessionInfo(httpServletRequest,"sessionId");
        Optional<Company> optionalCompany = companyService.loginIdCheck(sessionId);
        Company company;
        if(optionalCompany.isPresent()){
            company=optionalCompany.get();
            model.addAttribute("comName",company.getComName()); // 회사명
            model.addAttribute("comBusiNum", company.getComBusiNum()); // 사업등록번호
            model.addAttribute("comDiv", company.getComComdivCode().getComdivName()); // 회사분류
        }
        model.addAttribute("occList", postService.getListOccupation()); // 직업 리스트
        model.addAttribute("regionList", postService.getListRegion()); // 지역 리스트
        model.addAttribute("salaryList", postService.getListSalary()); // 연봉대 리스트
        return "/post/postInsertForm";
    }

    @PostMapping("/savePost")
    public String postSave(PostInsertDTO postInsertDTO, HttpServletRequest httpServletRequest) throws IOException {
        log.info("postInsertDTO={}",postInsertDTO);
        postInsertDTO.setComLoginId(getSessionInfo(httpServletRequest,"sessionId"));
        postService.savePost(postInsertDTO);
        log.info(postInsertDTO);
        return "redirect:/";
    }





    // HttpServletRequest 에서 두번째 파라미터 값에 따라 Session 정보를 리턴해주는 메소드.
    // @param typeOrSessionId
    // "type" -> "user" or "company"
    // "sessionId" -> 유저나 기업회원 로그인 ID 값
    private String getSessionInfo(HttpServletRequest httpServletRequest, String typeOrSessionId){
        HttpSession httpSession = httpServletRequest.getSession(false);
        // 세션 타입 체크
        if (typeOrSessionId.equals("Type")) {
            return httpSession.getAttribute("Type").toString();
        } else if (typeOrSessionId.equals("sessionId")) {
            return httpSession.getAttribute("sessionId").toString();
        } else {
            return null;
        }

    }
    @PostMapping(value = {"/remove"})
    public String remove(long id,RedirectAttributes redirectAttributes){
        postService.remove(id);
        redirectAttributes.addFlashAttribute("msg",id);
        return "redirect:/post/list";
    }




    // 삭제 예정     ================================================

    // "/list?type=title" 종류( title, company, occupation, titleCompanyName )
    // "/list?sort=new" 종류( new, count, salary, end )
    // "/list?outOfDateState" 종류( active, beforeStart, afterEnd, beforeAfter, all)
    // 자세한 설명은 PageRequestDTO 에 있습니다.
    @GetMapping(value = {"/list"})
    public String list(PageRequestDTO pageRequestDTO, Model model){
        PageResultDTO<Post, PostDTO> result = postService.getList(pageRequestDTO);
        model.addAttribute("result",result);
        return "/post/list";
//        return "/searchPage";
    }
    // "/list/com?type=title" 종류( title, company, occupation, titleCompanyName )
    // "/list/com?sort=new" 종류( new, count, salary, end )
    // "/list/com?outOfDateState" 종류( active, beforeStart, afterEnd, beforeAfter, all)
    // 자세한 설명은 PageRequestDTO 에 있습니다.
    @GetMapping(value = {"/list/com"})
    public String listComPage(PageRequestDTO pageRequestDTO, HttpServletRequest httpServletRequest, Model model){
        String type = getSessionInfo(httpServletRequest,"Type");
        // 세션 타입 체크
        pageRequestDTO.setAuthType(type);
        pageRequestDTO.setAuth(getSessionInfo(httpServletRequest,"sessionId"));

        model.addAttribute("result",postService.getList(pageRequestDTO));
        return "/post/list";
    }

    @PostMapping(value = {"/register"})
    public String register(PostDTO postDTO,HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Model model) throws ParseException {
        postDTO.setComLoginId(getSessionInfo(httpServletRequest,"sessionId"));
        Long postId = postService.register(postDTO);
        redirectAttributes.addFlashAttribute("msg",postId);
        return "redirect:/post/list";
    }
    @GetMapping(value={"/register"})
    public String register(HttpServletRequest httpServletRequest,Model model){
        model.addAttribute("occList", occupationService.getAll());
        return "/post/register";
    }
    @GetMapping(value = {"/read"})
    public String read(Long postId, PageRequestDTO pageRequestDTO, Model model){
        PostDTO dto = postService.read(postId);
        model.addAttribute("occList", occupationService.getAll());
        model.addAttribute("dto",dto);
//        return "/post/read";
        return "/post/postDetails";
    }
    @GetMapping(value = {"/modify"})
    public String modify(Long postId, PageRequestDTO pageRequestDTO, Model model){
        PostDTO dto = postService.read(postId);
        model.addAttribute("occList", occupationService.getAll());
        model.addAttribute("dto",dto);
        return "/post/modify";
    }
    @PostMapping(value={"/modify"})
    public String modify(PostDTO postDTO, PageRequestDTO pageRequestDTO,HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) throws ParseException {
        String sessionId = getSessionInfo(httpServletRequest,"sessionId");
        postDTO.setComLoginId(sessionId);
        postService.register(postDTO);
        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("postId",postDTO.getId());
        return "redirect:/post/read";
    }


}
