package com.goodjob.post.occupation.controller;

import com.goodjob.post.occupation.occupationdto.OccupationDto;
import com.goodjob.post.occupation.service.OccupationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/occupation"})
public class OccupationController {

    private final OccupationService occupationService;

    // 메인페이지 이동 겟
    @GetMapping(value = {"/",""})
    public String toMain(){
        return "/post/main/admin";
    }

    // 직종 전체 데이터(id, 직종명)를 가져오는 메소드
//    @GetMapping(value={"/getAll"})
//    @ResponseBody
//    public ResponseEntity<ResultDto<Occupation, OccupationDto>> getAll(){
//       return new ResponseEntity<>(oService.getAll(), HttpStatus.OK);
//    }
    // 직종 이름 조회해서 ID 가져오기
    @PostMapping(value={"/get"})
    @ResponseBody
    public ResponseEntity<OccupationDto> get(@RequestBody OccupationDto occupationDto){
        System.out.println(occupationDto);
        return new ResponseEntity<>(occupationService.get(occupationDto.getOccName()),HttpStatus.OK);
    }
    // 직종 저장, 수정용 메소드
    @PostMapping(value = {"/save"})
    public String save(OccupationDto occupationDto){
        occupationService.save(occupationDto);
        return "redirect:/occupation";
    }
    // 직종 삭제 메소드
    @PostMapping(value = {"/delete"})
    public String delete(OccupationDto occupationDto){
        occupationService.delete(occupationDto);
        return "redirect:/occupation";
    }
}
