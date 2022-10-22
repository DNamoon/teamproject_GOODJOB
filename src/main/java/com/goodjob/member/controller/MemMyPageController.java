package com.goodjob.member.controller;

import com.goodjob.member.memDTO.MemberDTO;
import com.goodjob.member.service.MemberService;
import com.goodjob.resume.dto.ResumeListDTO;
import com.goodjob.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 김도현 22.10.12 작성
 * 박채원 22.10.23 수정 - 이력서관리로 이동하는 메소드 추가
 * **/
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemMyPageController {

    private final MemberService memberService;
    private final ResumeService resumeService;

    @RequestMapping("/myPage")
    public String myPageForm(HttpSession session, Model model){
        String id = (String)session.getAttribute("sessionId");
        model.addAttribute("memberInfo",memberService.memInfo(id));
        return "member/myPageInfo";
    }

    @PostMapping("/myPageInfo")
    public String infoUpdate(MemberDTO memberDTO){
        memberService.updateMemInfo(memberDTO);
        return "redirect:/member/myPage";
    }

    @GetMapping("/myPageResume")
    public String myPageResume(){
        return "/member/myPageResumeList";
    }

    //박채원 - restful api 사용해서 리스트 뿌리는 거 해보려고 작성한 메소드
    @ResponseBody
    @GetMapping(value = "/getResumeList/{memId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResumeListDTO>> getResumeList(@PathVariable("memId") Long memId){
        return new ResponseEntity<>(resumeService.getResumeList(memId), HttpStatus.OK);
    }

//    @RequestMapping("/delete")
//    public String deleteMember() {
//        // mypage에서 탈퇴 버튼 생성(비밀번호 확인 후 회원 정보 삭제,이력서 정보 같이 삭제)
//        return 썸띵;
//    }
}
