package com.goodjob.status;

import com.goodjob.member.service.MailService;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.status.dto.ApplierListDTO;
import com.goodjob.status.dto.ApplyListDTO;
import com.goodjob.status.dto.IntervieweeListDTO;
import com.goodjob.status.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

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
   public void applyResume(@PathVariable("postId") Long postId, @RequestParam("selectResumeId") Long resumeId, HttpSession session) throws Exception {
      String id = (String) session.getAttribute("sessionId");
      statusService.applyResume(postId, resumeId, id);
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
   public void changePass(@PathVariable("statId") Long statId, @RequestParam("applyResult") String result){
      if(result.equals("서류합격")) {
         statusService.changePass(statId, result);
         mailService.sendMailToResumeApplier(statusService.getApplierToSendMail(statId));
      }else if(result.equals("최종합격")){
         statusService.changePass(statId, result);
         mailService.sendMailToResumeApplier(statusService.getApplierToSendMail(statId));
      }
   }

   //불합격
   @ResponseBody
   @GetMapping("/changeUnPass/{statId}")
   public void changeUnPass(@PathVariable("statId") Long statId, @RequestParam("applyResult") String result){
      if(result.equals("서류불합격")){
         statusService.changeUnPass(statId, result);
         mailService.sendMailToResumeApplier(statusService.getApplierToSendMail(statId));
      }else if(result.equals("면접불합격")){
         statusService.changeUnPass(statId, result);
         mailService.sendMailToResumeApplier(statusService.getApplierToSendMail(statId));
      }
   }

   //합격결과 있으면 로그인 시 팝업 띄우기
   @ResponseBody
   @PostMapping("/changeStatShow")
   public void changeStatShow(HttpSession session){
      String id = (String) session.getAttribute("sessionId");
      statusService.changeStatShow(id);
   }

   //면접 리스트 띄우기
   @ResponseBody
   @GetMapping("/getIntervieweeList/{postId}/{pageNum}")
   public ResponseEntity<PageResultDTO<IntervieweeListDTO, Status>> getIntervieweeApplierList(HttpSession session, @PathVariable("postId") Long postId, @PathVariable("pageNum") int pageNum){
      return new ResponseEntity<>(statusService.getIntervieweeList((String) session.getAttribute("sessionId"), postId, pageNum), HttpStatus.OK);
   }

   //면접 일정 메일로 전송
   @ResponseBody
   @GetMapping("/updateInterviewInfo/{statId}")
   public void updateInterviewInfo(@PathVariable("statId") Long statId, @RequestParam("place") String place, @RequestParam("date") String date){
      statusService.updateInterviewInfo(statId, place, LocalDateTime.parse(date));
      mailService.sendMailAboutInterviewInfo(statusService.getIntervieweeToSendMail(statId));
   }
}
