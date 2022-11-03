package com.goodjob.admin.controller;

import com.goodjob.admin.Admin;
import com.goodjob.admin.AdminConst;
import com.goodjob.admin.admindto.AdminDTO;
import com.goodjob.admin.postpaging.AdminPostService;
import com.goodjob.admin.service.AdminService;
import com.goodjob.post.Post;
import com.goodjob.post.postdto.PostInsertDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final AdminPostService adminPostService;

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
        session.setMaxInactiveInterval(60 * 100);
        return "redirect:/admin";
    }

    @GetMapping("/postManage/{pageNum}")
    public String postListForm(@PathVariable int pageNum, Model model) {
        pageNum = (pageNum == 0) ? 0 : (pageNum - 1);
        Sort sort = Sort.by("PostId").descending();
        Pageable pageable = PageRequest.of(pageNum, 10, sort);
        Page<Post> postList = adminPostService.findPostList(pageable);
        model.addAttribute("postPage", postList);
        return "/admin/managePage/adminPostManage";
    }

    @GetMapping("/memberManage")
    public String adminMemberPage(){
        return "/admin/managePage/adminMemberManage";
    }

    @GetMapping("/customerInquiry")
    public String adminCustomerInquiryList(){
        return "admin/customerInquiry/customerInquiryList";
    }


    /**
     * 22.10.30 오성훈 이하 테스트메소드 차후 삭제예정.
     */
    @GetMapping("/test")
    public String test(){
        return "post/postDetailViewWithMap";
    }

    @PostMapping("/test")
    public String test2(@ModelAttribute PostInsertDTO post){
            return "admin/adminHome";
    }
    @GetMapping("/test2")
    public String test3(){
        return "post/postDetailView";
    }
}
