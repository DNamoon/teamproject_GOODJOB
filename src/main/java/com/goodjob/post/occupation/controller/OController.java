package com.goodjob.post.occupation.controller;

import com.goodjob.post.occupation.Occupation;
import com.goodjob.post.occupation.service.OService;
import com.goodjob.post.occupation.occupationdto.OccupationDto;
import com.goodjob.post.util.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/o"})
public class OController {

    private final OService oService;

    // 메인페이지 이동 겟
    @GetMapping(value = {"/",""})
    public String toMain(){
        return "/post/main/admin";
    }

    // 직종 전체 데이터(id, 직종명)를 가져오는 메소드
    @GetMapping(value={"/getAll"})
    @ResponseBody
    public ResponseEntity<ResultDto<Occupation, OccupationDto>> getAll(){
       return new ResponseEntity<>(oService.getAll(), HttpStatus.OK);
    }
    // 직종 이름 조회해서 ID 가져오기
    @PostMapping(value={"/get"})
    @ResponseBody
    public ResponseEntity<OccupationDto> get(@RequestBody OccupationDto occupationDto){
        System.out.println(occupationDto);
        return new ResponseEntity<>(oService.get(occupationDto.getOccName()),HttpStatus.OK);
    }
    // 직종 저장, 수정용 메소드
    @PostMapping(value = {"/save"})
    public String save(OccupationDto occupationDto){
        oService.save(occupationDto);
        return "redirect:/o";
    }
    // 직종 삭제 메소드
    @PostMapping(value = {"/delete"})
    public String delete(OccupationDto occupationDto){
        oService.delete(occupationDto);
        return "redirect:/o";
    }

    // 테스트 용도 겟 메소드
//    @GetMapping(value = {"/test"})
//    public String test1(){
//        return "/post/main/complete";
//    }

    // 포스트 전체 가져오기
    // 포스트 개별 가져오기 path variable
    // 포스트 저장 및 수정
    // 포스트 삭제
}
