package com.goodjob.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/member")
@RequiredArgsConstructor
public class MemMyPageController {

    @GetMapping("/myPage")
    public String myPageForm(){
        return "member/mypage";
    }
}
