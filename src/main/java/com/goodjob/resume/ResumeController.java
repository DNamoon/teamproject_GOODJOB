package com.goodjob.resume;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodjob.career.dto.CareerDTO;
import com.goodjob.career.service.CareerService;
import com.goodjob.certification.CertificateName;
import com.goodjob.certification.dto.CertificationDTO;
import com.goodjob.certification.service.CertificationService;
import com.goodjob.education.MajorName;
import com.goodjob.education.SchoolName;
import com.goodjob.education.dto.EducationDTO;
import com.goodjob.education.service.EducationService;
import com.goodjob.member.memDTO.ResumeMemberDTO;
import com.goodjob.member.service.MemberService;
import com.goodjob.resume.service.ResumeService;
import com.goodjob.selfIntroduction.service.SelfIntroductionService;
import com.goodjob.selfIntroduction.dto.SelfIntroductionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 박채원 22.10.02 작성
 * 박채원 22.10.08 수정
 * resumeStep3 메소드 수정 - 뷰에서 가져온 자격증 리스트 정보 DB에 추가하는 부분
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;
    private final MemberService memberService;
    private final EducationService educationService;
    private final CareerService careerService;
    private final CertificationService certificationService;
    private final SelfIntroductionService selfIntroductionService;

    @ResponseBody
    @GetMapping("/registerResume")
    public Long registerResume(HttpSession session){
        String id = (String)session.getAttribute("sessionId");
        return resumeService.registerResume(id);
    }

    @GetMapping("/resumeStep1/{resumeId}")
    public String resumeStep1(@PathVariable("resumeId") Long resumeId, Model model, HttpSession session){
        String id = (String)session.getAttribute("sessionId");
        ResumeMemberDTO resumeMemberDTO = memberService.bringMemInfo(id);
        educationService.temporalSchoolInfo(resumeId);

        model.addAttribute("resumeId", resumeId);
        model.addAttribute("memberInfo", resumeMemberDTO);

        return "/resume/ResumeStep1";
    }

    //학교 검색할 때 AJAX에서 사용하는 메소드
    @ResponseBody
    @GetMapping("/findSchool/{schoolName}")
    public List<SchoolName> findSchool(@PathVariable("schoolName") String schoolName){
        return educationService.findSchoolName(schoolName);
    }

    //전공 검색할 때 AJAX에서 사용하는 메소드
    @ResponseBody
    @GetMapping("/findMajor/{majorName}")
    public List<MajorName> findMajor(@PathVariable("majorName") String majorName){
        return educationService.findMajorName(majorName);
    }

    //자격증 검색할 때 AJAX에서 사용하는 메소드
    @ResponseBody
    @GetMapping("/findCerti/{certiName}")
    public List<CertificateName> findCerti(@PathVariable("certiName") String certiName){
        return certificationService.findCertiName(certiName);
    }

    @GetMapping("/resumeStep2/{resumeId}")
    public String resumeStep2(@PathVariable("resumeId") Long resumeId, ResumeMemberDTO resumeMemberDTO, EducationDTO educationDTO, Model model){

        resumeService.updateResumeMemberInfo(resumeMemberDTO, resumeId);
        educationService.registerSchoolInfo(educationDTO);

        if(certificationService.existOrNotResumeId(resumeId) == 1){
            model.addAttribute("resumeId", resumeId);
            model.addAttribute("certiInfo", certificationService.bringCertiInfo(resumeId));
            model.addAttribute("careerInfo", careerService.bringCareerInfo(resumeId));

            return "/resume/ResumeStep2WithContent";
        }
        model.addAttribute("resumeId", resumeId);
        return "/resume/ResumeStep2";
    }

    @ResponseBody
    @RequestMapping(value = "/insertStep2", method = RequestMethod.GET)
    public void resumeStep3(@RequestParam Map params) throws Exception{
        String certiJson = params.get("certificateList").toString();
        String careerJson = params.get("careerList").toString();

        ObjectMapper mapper = new ObjectMapper();
        List<CertificationDTO> certiList = mapper.readValue(certiJson, new TypeReference<List<CertificationDTO>>(){});
        List<CareerDTO> careerList = mapper.readValue(careerJson, new TypeReference<List<CareerDTO>>() {});

        certificationService.registerCertiInfo(certiList);
        careerService.registerCareerInfo(careerList);
    }

    @ResponseBody
    @GetMapping("/addCertiInfo/{resumeId}")
    public Long addNullCertiInfo(@PathVariable("resumeId") Long resumeId){
        return certificationService.addNullCertiInfo(resumeId);
    }

    @ResponseBody
    @GetMapping("/addCareerInfo/{resumeId}")
    public Long addNullCareerInfo(@PathVariable("resumeId") Long resumeId){
        return careerService.addNullCareerInfo(resumeId);
    }

    @GetMapping("/resumeStep3/{resumeId}")
    public String resumeStep3(@PathVariable("resumeId") Long resumeId, Model model){

        if(selfIntroductionService.existOrNotResumeId(resumeId) == 1){
            model.addAttribute("resumeId", resumeId);
            model.addAttribute("selfIntroInfo", selfIntroductionService.bringSelfIntroInfo(resumeId));

            return "/resume/ResumeStep3WithContent";
        }

        model.addAttribute("resumeId", resumeId);
        return "/resume/ResumeStep3";
    }

    @GetMapping("/submitResume/{resumeId}")
    public String submitResume(SelfIntroductionDTO selfIntroductionDTO, @PathVariable("resumeId") Long resumeId){
        selfIntroductionService.registerSelfInfo(selfIntroductionDTO);
        //박채원 22.11.10 추가 (이하 1줄) - 지원할 때 표시되는 이력서 리스트에서 최종제출 되지 않은 이력서는 띄우지 않기 위함
        resumeService.setSubmittedTrue(resumeId);
        return "redirect:/member/myPageResume";
    }

    @GetMapping("/goPreviousStep1/{resumeId}")
    public String goPreviousStep1(@PathVariable("resumeId") Long resumeId, Model model){
        model.addAttribute("resumeId", resumeId);
        model.addAttribute("resumeMemInfo", resumeService.bringResumeInfo(resumeId));
        model.addAttribute("schoolInfo", educationService.bringSchoolInfo(resumeId));

        return "/resume/ResumeStep1WithContent";
    }

    @GetMapping("/goPreviousStep2/{resumeId}")
    public String goPreviousStep2(@PathVariable("resumeId") Long resumeId, SelfIntroductionDTO selfIntroductionDTO, Model model){
        selfIntroductionService.registerSelfInfo(selfIntroductionDTO);

        model.addAttribute("resumeId", resumeId);
        model.addAttribute("certiInfo", certificationService.bringCertiInfo(resumeId));
        model.addAttribute("careerInfo", careerService.bringCareerInfo(resumeId));

        return "/resume/ResumeStep2WithContent";
    }

    @ResponseBody
    @GetMapping("/deleteCertiList/{certiId}")
    public void deleteCertiList(@PathVariable("certiId") Long certiId){
        certificationService.deleteCertiInfo(certiId);
    }

    @ResponseBody
    @GetMapping("deleteCareerList/{careerId}")
    public void deleteCareerList(@PathVariable("careerId") Long careerId){
        careerService.deleteCareerList(careerId);
    }

    @ResponseBody
    @GetMapping("/deleteResume")
    public ResponseEntity<String> deleteResume(@RequestParam Map params) throws Exception {
        String resumeIdJson = params.get("resumeId").toString();
        ObjectMapper mapper = new ObjectMapper();

        List<String> resumeIdList = mapper.readValue(resumeIdJson, new TypeReference<List<String>>(){});
        resumeService.setDeleteResume(resumeIdList);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/changeTitle/{resumeId}")
    public void changeTitle(@PathVariable("resumeId") Long resumeId, @RequestParam("title") String title){
        resumeService.changeTitle(resumeId, title);
    }

    @GetMapping("/resumeRead/{memId}/{resumeId}")
    public String resumeReadForm(@PathVariable("memId") String memId, @PathVariable("resumeId") Long resumeId, Model model){

        model.addAttribute("resumeMemInfo", resumeService.bringResumeInfo(resumeId));
        model.addAttribute("schoolInfo", educationService.bringSchoolInfo(resumeId));
        model.addAttribute("certiInfo", certificationService.bringCertiInfo(resumeId));
        model.addAttribute("careerInfo", careerService.bringCareerInfo(resumeId));
        model.addAttribute("selfIntroInfo", selfIntroductionService.bringSelfIntroInfo(resumeId));

        return "/resume/ResumeRead";
    }

    @ResponseBody
    @GetMapping("/getMenuValue")
    public List<Integer> getMenuValue(HttpSession session){
        return resumeService.getResumeMenu((String) session.getAttribute("sessionId"));
    }
}