package com.goodjob.post.controller;

import com.goodjob.company.Company;
import com.goodjob.company.service.CompanyService;
import com.goodjob.post.Post;
import com.goodjob.post.occupation.service.OccupationService;
import com.goodjob.post.postdto.*;
import com.goodjob.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        postInsertDTO.setComLoginId(getSessionInfo(httpServletRequest,"sessionId"));
        postService.savePost(postInsertDTO);
        return "redirect:/post/comMyPagePost";
    }


    @GetMapping(value = {"/comMyPagePost"})
    public String comMyPagePost(PageRequestDTO pageRequestDTO, HttpServletRequest httpServletRequest, Model model){
        log.info("............"+pageRequestDTO);
        pageRequestDTO.setAuthType(getSessionInfo(httpServletRequest,"Type"));
        pageRequestDTO.setAuth(getSessionInfo(httpServletRequest,"sessionId"));
        PageResultDTO<Post, PostComMyPageDTO> result = postService.getPagingPostListInComMyPage(pageRequestDTO);
        model.addAttribute("occList", postService.getListOccupation()); // 직업 리스트
        model.addAttribute("regionList", postService.getListRegion()); // 지역 리스트
        model.addAttribute("salaryList", postService.getListSalary()); // 연봉대 리스트
        model.addAttribute("result",result);
        model.addAttribute("sessionLoginId",getSessionInfo(httpServletRequest,"sessionId"));
        log.info("................."+result);
        return "/post/comMyPagePost";
    }

    // postId 값으로 개별 공고를 삭제하는 메소드
    @DeleteMapping(value={"/deletePost/{postId}"})
    @ResponseBody
    @Transactional
    public ResponseEntity<String> deletePost(@PathVariable(name = "postId") Long postId){
        log.info("deletePost.........."+postId);
        postService.deletePost(postId);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }
    // postId 값으로 개별 공고를 조회하는 페이지로 이동하는 메소드
    @GetMapping(value = {"/readPost/{postId}"})
    @Transactional
    public String readPost(@PathVariable(name = "postId") Long postId, PageRequestDTO pageRequestDTO, Model model){
        log.info("deletePost.........."+postId);
        model.addAttribute("dto",postService.readPost(postId));
        return "/post/postDetailView";
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
}
