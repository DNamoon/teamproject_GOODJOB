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
        postDTO.setComLoginId(getAuthId(httpServletRequest,"sessionId"));
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
        postDTO.setComLoginId(getAuthId(httpServletRequest,"sessionId"));
        postService.register(postDTO);
        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("postId",postDTO.getId());
        return "redirect:/p/read";
    }

    @GetMapping(value = {"/list"})
    public String list(PageRequestDTO pageRequestDTO,HttpServletRequest httpServletRequest, Model model){
        log.info("Controller......." +pageRequestDTO);

        // 세션 타입 체크
        pageRequestDTO.setAuth(getAuthId(httpServletRequest,"Type"));

        model.addAttribute("result",postService.getList(pageRequestDTO));
        return "/post/list";
    }

    private String getAuthId(HttpServletRequest httpServletRequest, String typeOrSessionId){
        // 세션 타입 체크
        HttpSession httpSession = httpServletRequest.getSession();
        if(typeOrSessionId.equals("Type")){
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
