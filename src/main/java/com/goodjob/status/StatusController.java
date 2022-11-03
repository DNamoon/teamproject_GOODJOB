package com.goodjob.status;

import com.goodjob.member.service.MailService;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.status.dto.ApplierListDTO;
import com.goodjob.status.dto.ApplyListDTO;
import com.goodjob.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
   private final MailService mailService;

   //이력서 지원
   @ResponseBody
   @PostMapping("/applyResume/{postId}")
   public void applyResume(@PathVariable("postId") Long postId, @RequestParam("selectResumeId") Long resumeId){
      statusService.applyResume(postId, resumeId);
   }

   //개인 마이페이지에서 지원회사 목록 출력
   @ResponseBody
   @GetMapping(value = "/getApplyList/{pageNum}", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<PageResultDTO<ApplyListDTO, Status>> getApplyList(HttpSession session, @PathVariable("pageNum") int pageNum){
      return new ResponseEntity<>(statusService.getApplyList((String) session.getAttribute("sessionId"), pageNum), HttpStatus.OK);
   }

   //회사 마이페이지에서 지원자 목록 출력
   @ResponseBody
   @GetMapping("/getApplierList/{postId}/{pageNum}")
   public ResponseEntity<PageResultDTO<ApplierListDTO, Status>> getApplierList(HttpSession session, @PathVariable("postId") Long postId, @PathVariable("pageNum") int pageNum){
      return new ResponseEntity<>(statusService.getApplierList((String) session.getAttribute("sessionId"), postId ,pageNum), HttpStatus.OK);
   }

   //합격
   @ResponseBody
   @GetMapping("/changePass/{statId}")
   public void changePass(@PathVariable("statId") Long statId){
      statusService.changePass(statId);
      mailService.sendMailToResumeApplier(statusService.getApplierToSendMail(statId));
   }

   @ResponseBody
   @GetMapping("/changeUnPass/{statId}")
   public void changeUnPass(@PathVariable("statId") Long statId){
      statusService.changeUnPass(statId);
      mailService.sendMailToResumeApplier(statusService.getApplierToSendMail(statId));
   }

   @ResponseBody
   @PostMapping("/changeStatShow")
   public void changeStatShow(HttpSession session){
      System.out.println("컨트롤러 changeStatShow 실행");
      String id = (String) session.getAttribute("sessionId");
      System.out.println(id);
      statusService.changeStatShow(id);
   }
}
