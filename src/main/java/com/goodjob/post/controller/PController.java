package com.goodjob.post.controller;

import com.goodjob.post.postdto.PostBbsDto;
import com.goodjob.post.postdto.PostSaveDto;
import com.goodjob.post.service.PService;
import com.goodjob.post.util.PageRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/p")
@RequiredArgsConstructor
@Log4j2
public class PController {
    private final PService pService;

    @GetMapping(value={""})
    public String main(){
        return null;
    }


    // 공고 전체 리스트 html 이동
    @GetMapping(value = {"/postList"})
    public String getAll(Model model){

        model.addAttribute("postList", pService.getPostList().getDtoList());
        System.out.println(pService.getPostList().getDtoList());
        return "post/main/postList";
    }
    @GetMapping(value ={"/list"})
    public String  list(PageRequestDTO pageRequestDTO,Model model){
        log.info("list..................."+pageRequestDTO);
        model.addAttribute("result",pService.getList(pageRequestDTO));
        return "post/main/list";
    }

    // post Paging
    @RequestMapping(value = {"/bbs"}, method = {RequestMethod.GET,RequestMethod.POST})
//    public String getBbsByComId(Model model, HttpServletRequest httpServletRequest, @RequestParam(defaultValue = "0") int curPage, @RequestParam(defaultValue = "10") int numPerPage){
    public String getBbsByComId(Model model, HttpServletRequest httpServletRequest, @ModelAttribute PageRequestDTO pageRequestDTO){
        HttpSession httpSession = httpServletRequest.getSession();
        Sort sort = Sort.by(Sort.Direction.DESC,"postId");
        Pageable pageable = pageRequestDTO.getPageable(sort);
//        Pageable pageable = PageRequest.of(curPage, numPerPage, Sort.by(Sort.Direction.DESC,"postId"));
        Page<PostBbsDto> pageDto = pService.findAllPostsByComId(httpSession.getAttribute("sessionId").toString(),pageable);
        model.addAttribute("pageDto", pageDto);


        List<PostBbsDto> content = pageDto.getContent();

        if(!pageDto.isEmpty()){

            // for test
//            System.out.println(content);
//            System.out.println(httpSession.getAttribute("sessionId").toString());
//            System.out.println(pageDto);
//            System.out.println(httpSession);
//            System.out.println(pageable);
//            System.out.println(pageDto);

            return "post/main/tbl";
        } else {
            throw new Error("Error: invalid page request(NO CONTENTS)");
        }
    }

    // 공고 등록 폼으로 이동
    @GetMapping(value = {"/saveForm"})
    public String saveForm(Model model){

        return "post/main/registerPage";
    }

    // 1.5 공고 클릭시 공고 내용 가져오기 및 공고 페이지 리다이렉트
    // 2. GET 모집창에서 공고 관련 정보 가져오는 용도
    // - 회사 정보, 공고 정보
    @GetMapping(value = {"/postDetails/{postId}"})
    public String goToPost(@PathVariable Long postId, Model model){
        PostBbsDto postBbsDto = pService.getPost(postId);
        System.out.println(postBbsDto);
        model.addAttribute("postDetails", postBbsDto);
        // postId에 맞는 데이터를 가져오는 code
        return "post/main/postDetails";
    }


    // 공고 개별 가져오기
    // 공고 저장 및 수정 하기
    // 회사가 모집공고를 처음 저장하거나 수정 시 사용 메소드
    @PostMapping(value={"/save"})
    public String save(PostSaveDto postSaveDto, HttpServletRequest httpServletRequest){
        HttpSession httpSession = httpServletRequest.getSession();
        String comLoginId = httpSession.getAttribute("sessionID").toString();

        try {
            Long pno = pService.save(postSaveDto,comLoginId);
            if (pno == null) {
                throw new Exception("fails to save or update post");
            }

//            return "redirect:/post/goToPostList/";
            return "redirect:/p/postList";
        } catch (Exception e){
            System.out.println("저장 또는 수정 도중 에러가 발생했습니다.");
        }
        return null;
    }

}
