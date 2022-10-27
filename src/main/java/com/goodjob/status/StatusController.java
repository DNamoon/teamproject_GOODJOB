package com.goodjob.status;

import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.status.dto.ApplyDTO;
import com.goodjob.status.dto.ApplyListDTO;
import com.goodjob.status.repository.StatusRepository;
import com.goodjob.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 박채원 22.10.26 작성
 */

@Controller
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {

   private final StatusService statusService;
   private final StatusRepository statusRepository;

   @ResponseBody
   @PostMapping("/applyResume/{postId}")
   public void applyResume(@PathVariable("postId") Long postId, @RequestParam("selectResumeId") Long resumeId){
      statusService.applyResume(postId, resumeId);
   }

   @ResponseBody
   @GetMapping(value = "/getApplyList", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<ApplyListDTO>> getApplyList(HttpSession session){
      return new ResponseEntity<>(statusService.getApplyList((String) session.getAttribute("sessionId")), HttpStatus.OK);
//      return new ResponseEntity<>(statusService.getList(applyListDTO, (String) session.getAttribute("sessionId")), HttpStatus.OK);
   }

}
