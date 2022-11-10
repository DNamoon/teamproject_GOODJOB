package com.goodjob.resume.service;

import com.goodjob.member.Member;
import com.goodjob.member.memDTO.ResumeMemberDTO;
import com.goodjob.member.repository.MemberRepository;
import com.goodjob.resume.Resume;
import com.goodjob.resume.dto.ResumeListDTO;
import com.goodjob.resume.repository.ResumeRepository;
import com.goodjob.resume.dto.ResumeDTO;
import com.goodjob.status.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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
    private final StatusRepository statusRepository;

    @Override
    public Long registerResume(String loginId) {
        log.info("=========== 새 이력서 생성 ===========");
        Member member = memberRepository.findLoginInfo(loginId);
        Resume resume = Resume.builder()
                .resumeMemId(member).resumeMemAddress(member.getMemAddress()).resumeMemName(member.getMemName()).resumeMemGender(member.getMemGender())
                .resumeMemEmail(member.getMemEmail()).resumeMemPhone(member.getMemPhone()).resumeMemBirthDate(member.getMemBirthDate())
                .build();

        resumeRepository.save(resume);
        return resume.getResumeId();
    }

    @Override
    public void updateResumeMemberInfo(ResumeMemberDTO resumeMemberDTO, Long resumeId) {
        String mergePhoneNum = resumeMemberDTO.getMemFirstPhoneNum() + '-' + resumeMemberDTO.getMemMiddlePhoneNum() + '-' + resumeMemberDTO.getMemLastPhoneNum();
        String mergeAddress = resumeMemberDTO.getMemFirstAddress() + '@' + resumeMemberDTO.getMemLastAddress();
        String mergeEmail = resumeMemberDTO.getMemFirstEmail() + '@' + resumeMemberDTO.getMemLastEmail();

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
    public List<ResumeListDTO> getResumeList(String loginId, String type) {
        List<Resume> resumeList = new ArrayList<>();
        if(type.equals("my")){
            //회원의 이력서 관리에 가져오는 이력서 리스트는 삭제 컬럼이 false인지만 비교해서 가져옴
            resumeList = resumeRepository.getResumeByResumeMemId_MemLoginIdAndDeletedOrderByResumeId(loginId, false);
        }else{
            //지원하기의 이력서 리스트는 삭제 컬럼이 false인지와 최종제출 컬럼이 true인지를 비교해서 가져옴
            resumeList = resumeRepository.getResumeByResumeMemId_MemLoginIdAndDeletedAndSubmittedOrderByResumeId(loginId, false, true);
        }
        return resumeList.stream().map(resume -> entityToListDTO(resume)).collect(Collectors.toList());
    }

    @Override
    public void setDeleteResume(List<String> resumeId) {
        //박채원 22.11.02 추가
        //사용자가 지원 이력이 있는 이력서를 삭제하면 실제로 DB에서 삭제되는게 아니라 컬럼만 바꾸고 화면에는 보여주지 않음
        for(String id : resumeId){
            if(statusRepository.countStatusByStatResumeId_ResumeId(Long.valueOf(id)) > 0){
                resumeRepository.setDelete(Long.valueOf(id));
            }else{
                //지원 이력이 없는 이력서는 DB에서도 삭제함
                resumeRepository.deleteByResumeId(Long.valueOf(id));
            }
        }
    }

    @Override
    public void changeTitle(Long resumeId, String title) {
        resumeRepository.changeTitle(title, resumeId);
    }

    @Override
    public List<Integer> getResumeMenu(String loginId) {
        List<Integer> menuList = new ArrayList<>();
        menuList.add(resumeRepository.countResumeByResumeMemId_MemLoginIdAndDeleted(loginId, false));
        menuList.add(statusRepository.countStatusByStatResumeId_ResumeMemId_MemLoginId(loginId));
        menuList.add(statusRepository.countStatusByStatResumeId_ResumeMemId_MemLoginIdAndStatPassContains(loginId, "최종합격"));
        menuList.add(statusRepository.countStatusByStatResumeId_ResumeMemId_MemLoginIdAndStatPassContains(loginId, "불합격"));
        return menuList;
    }

    @Override
    public void setSubmittedTrue(Long resumeId) {
        resumeRepository.setSubmitted(resumeId);
    }
}
