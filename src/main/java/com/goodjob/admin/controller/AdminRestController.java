package com.goodjob.admin.controller;

import com.goodjob.admin.admindto.GenderDTO;
import com.goodjob.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminRestController {

    private final MemberRepository memberRepository;

    @GetMapping("/test")
    public GenderDTO test() {
        GenderDTO genderDTO = new GenderDTO();
        Integer female = memberRepository.countByMemGender("F");
        Integer male = memberRepository.countByMemGender("M");
        genderDTO.setFemale(female);
        genderDTO.setMale(male);
        return genderDTO;
    }
}
