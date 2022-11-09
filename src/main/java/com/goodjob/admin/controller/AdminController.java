package com.goodjob.admin.controller;

import com.goodjob.admin.Admin;
import com.goodjob.admin.AdminConst;
import com.goodjob.admin.admindto.AdminDTO;
import com.goodjob.admin.postpaging.AdminPostService;
import com.goodjob.admin.service.AdminService;
import com.goodjob.customerInquiry.CustomerInquiryPost;
import com.goodjob.customerInquiry.service.CustomerInquiryService;
import com.goodjob.post.Post;
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
import java.time.LocalDateTime;

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
    private final CustomerInquiryService customerInquiryService;

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
    public String adminMemberPage() {
        return "/admin/managePage/adminMemberManage";
    }

    @GetMapping("/customerInquiry/{pageNum}")
    public String adminCustomerInquiryList(@PathVariable("pageNum") int pageNum, Model model,
                                           @RequestParam("sort") String sortType,
                                           @RequestParam(value = "category", required = false) String category) {
        if (category != null) {
            Pageable Pageable = PageRequest.of(pageNum - 1, 10, Sort.by(sortType).descending());
            model.addAttribute("inquiryPostList", customerInquiryService.findAllByCategory(Pageable, category));
            model.addAttribute("sortType", sortType);
            return "admin/customerInquiry/customerInquiryList";
        }
        if (sortType.equals("inquiryPostComId")) {
            Pageable Pageable = PageRequest.of(pageNum - 1, 10, Sort.by("inquiryPostId").descending());
            model.addAttribute("inquiryPostList", customerInquiryService.findAllByMemberType(Pageable, "inquiryPostComId_comId"));
            model.addAttribute("sortType", sortType);
            return "admin/customerInquiry/customerInquiryList";
        }
        if (sortType.equals("inquiryPostMemberId")) {
            Pageable Pageable = PageRequest.of(pageNum - 1, 10, Sort.by("inquiryPostId").descending());
            model.addAttribute("inquiryPostList", customerInquiryService.findAllByMemberType(Pageable, "inquiryPostMemberId_memId"));
            model.addAttribute("sortType", sortType);
            return "admin/customerInquiry/customerInquiryList";
        }
        if (sortType.equals("inquiryPostStatus")) {
            Pageable Pageable = PageRequest.of(pageNum - 1, 10, Sort.by("inquiryPostId").descending());
            model.addAttribute("inquiryPostList", customerInquiryService.findAllByInquiryPostStatus(Pageable, "0"));
            model.addAttribute("sortType", sortType);
            return "admin/customerInquiry/customerInquiryList";
        }

        Pageable Pageable = PageRequest.of(pageNum - 1, 10, Sort.by(sortType).descending());
        model.addAttribute("sortType", sortType);
        model.addAttribute("inquiryPostList", customerInquiryService.findAll(Pageable));
        return "admin/customerInquiry/customerInquiryList";
    }

    @GetMapping("/customerInquiry/{id}/detail")
    public String inquiryPostView(@PathVariable("id") Long id, Model model) {
        model.addAttribute("findInquiry", customerInquiryService.findOne(id).orElse(null));
        return "admin/customerInquiry/customerInquiryDetailView";
    }

    @PostMapping("/customerInquiry/{id}/detail")
    public String inquiryPostReply(@PathVariable("id") Long id, @RequestParam("requestURL") String requestURL,
                                   @RequestParam("inquiryPostAnswer") String inquiryPostAnswer) {
        customerInquiryService.updateInquiryPostWithAnswer(id,
                inquiryPostAnswer,
                AdminConst.ADMIN,
                LocalDateTime.now(),
                "1"
        );
        return "redirect:" + requestURL;
    }

    @GetMapping("/customerInquiry/search/{pageNum}")
    public String inquiryPostSearch(@PathVariable("pageNum") int pageNum,
                                    @RequestParam("searchCategory") String searchCategory,
                                    @RequestParam("searchText") String searchText,
                                    Model model) {
        if (searchCategory.equals("searchById")) {
            Pageable pageable = PageRequest.of(pageNum - 1, 10, Sort.by("inquiryPostId").descending());
            Page<CustomerInquiryPost> allByWriter = customerInquiryService.findAllByWriter(pageable, searchText);
            model.addAttribute("inquiryPostList", allByWriter);
            return "admin/customerInquiry/customerInquiryList";
        }
        if (searchCategory.equals("searchByTitle")){
            Pageable pageable = PageRequest.of(pageNum - 1, 10, Sort.by("inquiryPostId").descending());
            Page<CustomerInquiryPost> allByWriter = customerInquiryService.findAllByTitle(pageable, searchText);
            model.addAttribute("inquiryPostList", allByWriter);
            return "admin/customerInquiry/customerInquiryList";
        }
        if (searchCategory.equals("searchByContent")){
            Pageable pageable = PageRequest.of(pageNum - 1, 10, Sort.by("inquiryPostId").descending());
            Page<CustomerInquiryPost> allByWriter = customerInquiryService.findAllByContent(pageable, searchText);
            model.addAttribute("inquiryPostList", allByWriter);
            return "admin/customerInquiry/customerInquiryList";
        }
        return "admin/customerInquiry/customerInquiryList";
    }

}
