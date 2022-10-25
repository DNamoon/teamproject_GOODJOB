package com.goodjob.post;

import com.goodjob.post.occupation.service.OService;
import com.goodjob.post.postdto.PageRequestDTO;
import com.goodjob.post.postdto.PostDTO;
import com.goodjob.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

@Log4j2
@Controller
@RequestMapping(value = {"/p"})
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final OService oService;

    @PostMapping(value = {"/register"})
    public String register(PostDTO postDTO,HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes, Model model) throws ParseException {
        log.info("controller........register"+postDTO);
        HttpSession httpSession = httpServletRequest.getSession();
        postDTO.setComLoginId(getAuthId(httpSession,"sessionId"));
        Long postId = postService.register(postDTO);
        redirectAttributes.addFlashAttribute("msg",postId);
        return "redirect:/p/list";
    }
    @GetMapping(value={"/register"})
    public String register(HttpServletRequest httpServletRequest,Model model){
        model.addAttribute("occList",oService.getAll());
        return "/post/register";
    }
    @GetMapping(value = {"/read"})
    public String read(Long postId, PageRequestDTO pageRequestDTO, Model model){
        log.info("controller.......read...."+postId+pageRequestDTO);
        PostDTO dto = postService.read(postId);
        log.info(dto);
        model.addAttribute("occList",oService.getAll());
        model.addAttribute("dto",dto);
        return "/post/read";
    }
    @GetMapping(value = {"/modify"})
    public String modify(Long postId, PageRequestDTO pageRequestDTO, Model model){
        log.info("controller.......read...."+postId+ pageRequestDTO);
        PostDTO dto = postService.read(postId);
        model.addAttribute("occList",oService.getAll());
        model.addAttribute("dto",dto);
        return "/post/modify";
    }
    @PostMapping(value={"/modify"})
    public String modify(PostDTO postDTO, PageRequestDTO pageRequestDTO,HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) throws ParseException {
        log.info("controller......modify..."+postDTO+pageRequestDTO);
        HttpSession httpSession = httpServletRequest.getSession();
        postDTO.setComLoginId(getAuthId(httpSession,"sessionId"));
        postService.register(postDTO);
        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("postId",postDTO.getId());
        return "redirect:/p/read";
    }

    @GetMapping(value = {"/list"})
    public String list(PageRequestDTO pageRequestDTO, HttpServletRequest httpServletRequest, Model model){
        log.info("Controller......." +pageRequestDTO);
//        HttpSession httpSession = httpServletRequest.getSession();
//        log.info(httpSession);
//        if(!(httpSession.getAttribute("Type").toString()==null)) {
//            String type = getAuthId(httpSession,"Type");
//            pageRequestDTO.setAuth(type);
//        }
        model.addAttribute("result",postService.getList(pageRequestDTO));
        return "/post/list";
    }

    @GetMapping(value = {"/list/com","/list/user"})
    public String listComPage(PageRequestDTO pageRequestDTO, HttpServletRequest httpServletRequest, Model model){
        log.info("Controller......." +pageRequestDTO);
        HttpSession httpSession = httpServletRequest.getSession();
        String type = getAuthId(httpSession,"Type");
        // 세션 타입 체크
        pageRequestDTO.setAuthType(type);
        pageRequestDTO.setAuth(getAuthId(httpSession,"sessionId"));

        model.addAttribute("result",postService.getList(pageRequestDTO));
        return "/post/list";
    }



    private String getAuthId(HttpSession httpSession, String typeOrSessionId){
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
        log.info(id);
        postService.remove(id);
        redirectAttributes.addFlashAttribute("msg",id);
        return "redirect:/p/list";
    }


}
