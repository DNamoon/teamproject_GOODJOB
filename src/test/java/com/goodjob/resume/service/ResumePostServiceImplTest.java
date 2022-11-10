//package com.goodjob.resume.service;
//
//import com.goodjob.member.Member;
//import com.goodjob.member.memDTO.ResumeMemberDTO;
//import com.goodjob.resume.Resume;
//import com.goodjob.resume.dto.ResumeDTO;
//import com.goodjob.resume.dto.ResumeListDTO;
//import com.goodjob.resume.repository.ResumeRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class ResumeServiceImplTest implements ResumeService{
//
//    @Autowired
//    private ResumeRepository resumeRepository;
//
//    @Test
//    void deleteResume() {
////        resumeRepository.deleteById(355L);
//    }
//
//    @Override
//    public Long registerResume(String loginId) {
//        return null;
//    }
//
//    @Override
//    public void updateResumeMemberInfo(ResumeMemberDTO resumeMemberDTO, Long resumeId) {
//
//    }
//
//    @Override
//    public ResumeDTO bringResumeInfo(Long resumeId) {
//        return null;
//    }
//
////    @Test
////    public List<ResumeListDTO> getResumeList(String loginId) {
////        loginId = "member3921";
////        List<Resume> resumeList = resumeRepository.getResumeByResumeMemId_MemLoginIdOrderByResumeId(loginId);
////        System.out.println("============" + resumeList.stream().map(resume -> entityToListDTO(resume)).collect(Collectors.toList()));
////        return resumeList.stream().map(resume -> entityToListDTO(resume)).collect(Collectors.toList());
////    }
//
//    @Override
//    public void setDeleteResume(List<String> resumeId) {
//
//    }
//
//    @Override
//    public void changeTitle(Long resumeId, String title) {
//
//    }
//}