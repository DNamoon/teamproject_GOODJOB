package com.goodjob.resume;

import com.goodjob.career.dto.CareerDTO;
import com.goodjob.career.service.CareerService;
import com.goodjob.certification.CertificateName;
import com.goodjob.certification.dto.CertificationDTO;
import com.goodjob.certification.service.CertificationService;
import com.goodjob.education.MajorName;
import com.goodjob.education.SchoolName;
import com.goodjob.education.dto.EducationDTO;
import com.goodjob.education.service.EducationService;
import com.goodjob.member.dto.MemberDTO;
import com.goodjob.member.service.MemberService;
import com.goodjob.selfIntroduction.service.SelfIntroductionService;
import com.goodjob.resume.service.ResumeService;
import com.goodjob.selfIntroduction.dto.SelfIntroductionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 박채원 22.10.02 작성
 */

@Controller
@Log4j2
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final MemberService memberService;
    private final EducationService educationService;
    private final CareerService careerService;
    private final CertificationService certificationService;
    private final SelfIntroductionService selfIntroductionService;


    @GetMapping("/myInfo")
    public String registerButton(){
        return "ResumeRegisterButton";
    }

    @GetMapping("/resumeStep1")
    public String resumeStep1(Model model){
        String loginId = "memberId2";  //나중에 세션 처리하면 로그인된 아이디 가져오게 수정
        MemberDTO memberDTO = memberService.bringMemInfo(loginId);

        model.addAttribute("resumeId", resumeService.registerResume(loginId));  //새 이력서 등록을 여기서 하지말고 이력서 등록버튼있는 곳에서 ajax로 인서트하고 넘어오는건? 지금 문제가 스텝1에서 새로고침할 때마다 이력서가 생기니까
        model.addAttribute("memberInfo", memberDTO);
        return "ResumeStep1";
    }

    //학교 검색할 때 AJAX에서 사용하는 메소드
    @ResponseBody
    @GetMapping("/findSchool/{schoolName}")
    public List<SchoolName> findSchool(@PathVariable("schoolName") String schoolName){
        return educationService.findSchoolName(schoolName);
    }

    @ResponseBody
    @GetMapping("/findMajor/{majorName}")
    public List<MajorName> findMajor(@PathVariable("majorName") String majorName){
        return educationService.findMajorName(majorName);
    }

    @ResponseBody
    @GetMapping("/findCerti/{certiName}")
    public List<CertificateName> findCerti(@PathVariable("certiName") String certiName){
        return certificationService.findCertiName(certiName);
    }

    @PostMapping("/resumeStep2")
    public String resumeStep2(MemberDTO memberDTO, EducationDTO educationDTO, Model model){
        //여기서는 step1의 내용을 가지고 와서 서비스에서 dto -> entity 변환 후 db에 insert하는 과정 작성/ 인적사항은 업데이트임!!
        System.out.println(memberDTO);
        System.out.println(educationDTO);

//        memberService.updateMemberInfo(memberDTO);    아직 쓰면안됨 22.10.03
        educationService.registerSchoolInfo(educationDTO);
        model.addAttribute("resumeId", educationDTO.getResumeId());
        return "ResumeStep2";
    }
    
    @PostMapping("/resumeStep3")
    public String resumeStep3(CertificationDTO certificationDTO, CareerDTO careerDTO, Model model){
        certificationService.registerCertiInfo(certificationDTO);
        careerService.registerCareerInfo(careerDTO);
        model.addAttribute("resumeId", careerDTO.getResumeId());
        return "ResumeStep3";
    }

    @PostMapping("/submitResume")
    public String submitResume(SelfIntroductionDTO selfIntroductionDTO){
        selfIntroductionService.registerSelfInfo(selfIntroductionDTO);
        return "redirect:/myInfo";
    }

    @PostMapping("/goPreviousStep1")
    public String goPreviousStep1(CertificationDTO certificationDTO, CareerDTO careerDTO, Model model){
        String loginId = "memberId2";     //세션 처리 후 세션에서 가져올 값

        certificationService.existOrNotResumeId(certificationDTO);
        careerService.existOrNotResumeId(careerDTO);
        model.addAttribute("memberInfo", memberService.bringMemInfo(loginId));
        model.addAttribute("schoolInfo", educationService.bringSchoolInfo(certificationDTO.getResumeId()));
        return "ResumeStep1";
    }
}
