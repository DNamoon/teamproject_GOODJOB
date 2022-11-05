package com.goodjob.post.controller;

import com.goodjob.company.Company;
import com.goodjob.company.service.CompanyService;
import com.goodjob.post.Post;
import com.goodjob.post.Test;
import com.goodjob.post.error.SessionCompanyAccountNotFound;
import com.goodjob.post.error.SessionNotFoundException;
import com.goodjob.post.occupation.service.OccupationService;
import com.goodjob.post.postdto.*;
import com.goodjob.post.service.PostService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Controller
@RequestMapping(value = {"/post"})
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final OccupationService occupationService;
    private final CompanyService companyService;


    @PostMapping(value = {"/test"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<String> test(@ModelAttribute Test tn, HttpServletRequest httpServletRequest){

        log.info("테스트!!!!!!!!!!!!!!"+tn);
        if(tn.getFiles()==null){
            return new ResponseEntity<>("저장 실패",HttpStatus.OK);
        } else {
            log.info(tn.getFiles());
            return new ResponseEntity<>("저장 성공",HttpStatus.OK);
        }
    }
    private List<String> tokenizerStringToList(String keyword){
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(keyword," ");
        while(st.hasMoreTokens()){
            list.add(st.nextToken());
        }
        return list;
    }

    @GetMapping("/savePost")
    public String postSaveForm(String redirectedFrom ,HttpServletRequest httpServletRequest, Model model){

        String sessionId = getSessionInfo(httpServletRequest,"sessionId");
        model.addAttribute("comInfo",postService.getComInfo(sessionId)); // CompanyInfoDTO(회사 주소+이름+사업번호+구분)
        model.addAttribute("occList", postService.getListOccupation()); // 직업 리스트
        model.addAttribute("salaryList", postService.getListSalary()); // 연봉대 리스트
        model.addAttribute("redirectedFrom", redirectedFrom);
        return "/post/postInsertForm";
    }
    @PostMapping(value = "/savePost",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<?> postSave(@Valid @ModelAttribute PostInsertDTO postInsertDTO, HttpServletRequest httpServletRequest) throws IOException {
        log.info("===================="+postInsertDTO);
        postInsertDTO.setComLoginId(getSessionInfo(httpServletRequest,"sessionId"));
        Long savedPostId = postService.savePost(postInsertDTO);
        if(savedPostId!=null){
            return new ResponseEntity<Long>(savedPostId, HttpStatus.OK);
        } else {
            return null;
        }
    }

    @GetMapping(value = {"/updatePost/{postId}"})
    public String postUpdate(@PathVariable(name="postId")Long postId, String redirectedFrom, HttpServletRequest httpServletRequest, Model model){
        String sessionId = getSessionInfo(httpServletRequest,"sessionId");
        PostInsertDTO postInsertDTO = postService.getPostById(postId);
        model.addAttribute("comInfo",postService.getComInfo(sessionId));
        model.addAttribute("dto",postInsertDTO);
        model.addAttribute("occList", postService.getListOccupation()); // 직업 리스트
        model.addAttribute("salaryList", postService.getListSalary()); // 연봉대 리스트
        return "/post/postUpdateForm";
    }



    // 기업 마이 페이지 공고관리 처리 메소드
    @GetMapping(value = {"/comMyPagePost"})
    public String comMyPagePost(PageRequestDTO pageRequestDTO, HttpServletRequest httpServletRequest, Model model){
        log.info("............"+pageRequestDTO);
        pageRequestDTO.setAuthType(getSessionInfo(httpServletRequest,"Type"));
        pageRequestDTO.setAuth(getSessionInfo(httpServletRequest,"sessionId"));
        PageResultDTO<Post, PostComMyPageDTO> result = postService.getPagingPostListInComMyPage(pageRequestDTO);
        model.addAttribute("occList", postService.getListOccupation()); // 직업 리스트
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
        postService.deletePost(postId);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }
    // postId 값으로 개별 공고를 조회하는 페이지로 이동하는 메소드
    @GetMapping(value = {"/readPost/{postId}"})
    @Transactional
    public String readPost(@PathVariable(name = "postId") Long postId, HttpServletRequest httpServletRequest, PageRequestDTO pageRequestDTO, Model model){
        PostDetailsDTO postDetailsDTO = postService.readPost(postId);
        model.addAttribute("dto",postDetailsDTO);
        Boolean isCompanySession = getSessionInfo(httpServletRequest, "Type") != null;
        model.addAttribute("isCompanySession", isCompanySession);
        return "/post/postDetailViewWithMap";
    }

    // HttpServletRequest 에서 두번째 파라미터 값에 따라 Session 정보를 리턴해주는 메소드.
    // @param typeOrSessionId
    // "type" -> "user" or "company"
    // "sessionId" -> 유저나 기업회원 로그인 ID 값
    private String getSessionInfo(HttpServletRequest httpServletRequest, String typeOrSessionId){
        HttpSession httpSession = httpServletRequest.getSession(false);
        if(httpSession != null && httpSession.getAttribute("Type")=="company"){
            // 세션 타입 체크
            if (typeOrSessionId.equals("Type")) {
                return httpSession.getAttribute("Type").toString();
            } else if (typeOrSessionId.equals("sessionId")) {
                return httpSession.getAttribute("sessionId").toString();
            } else {
                throw new SessionCompanyAccountNotFound();
            }
        } else{
            throw new SessionNotFoundException();
        }
    }
}
