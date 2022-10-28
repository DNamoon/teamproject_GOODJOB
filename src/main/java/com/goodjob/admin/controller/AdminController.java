package com.goodjob.admin.controller;

import com.goodjob.admin.Admin;
import com.goodjob.admin.AdminConst;
import com.goodjob.admin.admindto.AdminDTO;
import com.goodjob.admin.postpaging.ArticlePage;
import com.goodjob.admin.postpaging.ArticlePageService;
import com.goodjob.admin.service.AdminService;
import com.goodjob.post.Post;
import com.goodjob.post.postdto.PostDTO;
import com.goodjob.post.postdto.PostInsertDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 관리자 컨트롤러 By.OH
 */
@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final ArticlePageService articlePageService;

    @GetMapping
    public String adminHome(@SessionAttribute(name = AdminConst.ADMIN, required = false) Admin admin, Model model) {
        if (admin != null) {
            return "admin/adminHome";
        }
        return "redirect:login";
    }

    @GetMapping("/login")
    public String adminLoginForm(@ModelAttribute("adminDTO") AdminDTO adminDTO) {
        return "admin/adminLoginForm";
    }

    @PostMapping("/login")
    public String adminLogin(@Validated @ModelAttribute("adminDTO") AdminDTO adminDTO, BindingResult bindingResult, HttpServletRequest request) {
        log.info("adminDTO = {}", adminDTO);

        if (bindingResult.hasErrors()) {
            return "/admin/adminLoginForm";
        }


        Admin loginAdmin = adminService.login(adminDTO.getAdLoginId(), adminDTO.getAdPw());

        if (loginAdmin == null) {
            bindingResult.reject("loginFail", "로그인에 실패하였습니다.");
            return "/admin/adminLoginForm";
        }

        HttpSession session = request.getSession();

        session.setAttribute(AdminConst.ADMIN, loginAdmin);
        session.setMaxInactiveInterval(60 * 10);
        return "redirect:/admin";
    }

    @GetMapping("/post/{pageNum}")
    public String postBbs(@PathVariable Long pageNum,Model model) {
        ArticlePage articlePage = articlePageService.getArticlePage(pageNum);
        model.addAttribute("postPage", articlePage);
        return "/admin/adminPostManage";
    }

    @GetMapping("/memberManage")
    public String adminMemberPage(){
        return "/admin/adminMemberManage";
    }


    @GetMapping("/test")
    public String test(){
        return "post/postInsertForm";
    }

    @PostMapping("/test")
    public String test2(@ModelAttribute PostInsertDTO post){
            log.info("postDTO={}",post);
            return "admin/adminHome";
    }
    @GetMapping("/test2")
    public String test3(){
        return "post/postDetailView";
    }
}
