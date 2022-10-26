package com.goodjob.status;

import com.goodjob.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 박채원 22.10.26 작성
 */

@Controller
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {

   private final StatusService statusService;

   @ResponseBody
   @PostMapping("/applyResume/{postId}")
   public void applyResume(@PathVariable("postId") Long postId, @RequestParam("selectResumeId") Long resumeId){
      statusService.applyResume(postId, resumeId);
   }

}
