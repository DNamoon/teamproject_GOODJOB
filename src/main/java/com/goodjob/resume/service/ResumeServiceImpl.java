package com.goodjob.resume.service;

import com.goodjob.member.Member;
import com.goodjob.member.memDTO.ResumeMemberDTO;
import com.goodjob.member.repository.MemberRepository;
import com.goodjob.resume.Resume;
import com.goodjob.resume.dto.ResumeListDTO;
import com.goodjob.resume.repository.ResumeRepository;
import com.goodjob.resume.dto.ResumeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 박채원 22.10.03 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long registerResume(String loginId) {
        log.info("=========== 새 이력서 생성 ===========");
        Member member = memberRepository.findLoginInfo(loginId);
        Resume resume = Resume.builder()
                .resumeMemId(member).resumeMemAddress(member.getMemAddress())
                .resumeMemEmail(member.getMemEmail()).resumeMemPhone(member.getMemPhone())
                .build();

        resumeRepository.save(resume);
        return resume.getResumeId();
    }

    @Override
    public void updateResumeMemberInfo(ResumeMemberDTO resumeMemberDTO, Long resumeId) {
        String mergePhoneNum = resumeMemberDTO.getMemFirstPhoneNum() + '-' + resumeMemberDTO.getMemMiddlePhoneNum() + '-' + resumeMemberDTO.getMemLastPhoneNum();
        String mergeAddress = resumeMemberDTO.getMemFirstAddress() + '@' + resumeMemberDTO.getMemLastAddress();
        String mergeEmail = resumeMemberDTO.getMemFirstEmail() + '@' + resumeMemberDTO.getMemLastEmail();

        Resume resume = dtoToEntity(resumeMemberDTO, mergePhoneNum, mergeAddress, mergeEmail);

        log.info("=========== 이력서 인적사항 수정 ===========");
        resumeRepository.updateMemberInfo(mergePhoneNum, mergeEmail, mergeAddress, resumeId);
    }

    @Override
    public ResumeDTO bringResumeInfo(Long resumeId) {
        Resume resume = resumeRepository.findByResumeId(resumeId);
        String[] phoneNum = resume.getResumeMemPhone().split("-");
        String[] email = resume.getResumeMemEmail().split("@");
        String[] address = resume.getResumeMemAddress().split("@");

        ResumeDTO resumeDTO = entityToDTO(resume,phoneNum[0],phoneNum[1],phoneNum[2], email[0], email[1], address[0],address[1]);
        return resumeDTO;
    }

    @Override
    public List<ResumeListDTO> getResumeList(String loginId) {
        List<Resume> resumeList = resumeRepository.getResumeByResumeMemId_MemLoginIdOrderByResumeId(loginId);
        return resumeList.stream().map(resume -> entityToListDTO(resume)).collect(Collectors.toList());
    }

    @Override
    public void deleteResume(List<String> resumeId) {
        for(String id : resumeId){
            resumeRepository.deleteByResumeId(Long.valueOf(id));
        }
    }

    @Override
    public void changeTitle(Long resumeId, String title) {
        resumeRepository.changeTitle(title, resumeId);
    }

    @Override
    public ResumeDTO bringResumeInfoWithMemId(String memId, Long resumeId) {
        Resume resume = resumeRepository.findByResumeMemIdAndAndResumeId(memId, resumeId);
        String[] phoneNum = resume.getResumeMemPhone().split("-");
        String[] email = resume.getResumeMemEmail().split("@");
        String[] address = resume.getResumeMemAddress().split("@");

        ResumeDTO resumeDTO = entityToDTO(resume,phoneNum[0],phoneNum[1],phoneNum[2], email[0], email[1], address[0],address[1]);
        return resumeDTO;
    }

//    @Override
//    public void changeTitle(ResumeListDTO resumeListDTO) {
//        Resume resume = listDTOToEntity(resumeListDTO);
//        resumeRepository.save(resume);
//    }


}
