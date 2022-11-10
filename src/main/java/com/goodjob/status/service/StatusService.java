package com.goodjob.status.service;

import com.goodjob.post.Post;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.resume.Resume;
import com.goodjob.status.Status;
import com.goodjob.status.dto.*;

import java.time.LocalDateTime;

/**
 * 박채원 22.10.26 작성
 */

public interface StatusService {
    void applyResume(Long postId, Long resumeId, String loginId) throws Exception;

    PageResultDTO<ApplyListDTO, Status> getApplyList(String loginId, int pageNum);

    PageResultDTO<ApplierListDTO, Status> getApplierList(String loginId, Long postId, int pageNum);

    void changePass(Long statId, String result);

    void changeUnPass(Long statId, String result);

    SendMailDTO getApplierToSendMail(Long statId);

    boolean havePass(String loginId);

    void changeStatShow(String loginId);

    PageResultDTO<IntervieweeListDTO, Status> getIntervieweeList(String loginId, Long postId, int pageNum);

    void updateInterviewInfo(Long statId, String interviewPlace, LocalDateTime interviewDate);

    SendMailToIntervieweeDTO getIntervieweeToSendMail(Long statId);

    default Status dtoToEntity(Long postId, Long resumeId) {
        Post post = Post.builder().postId(postId).build();
        Resume resume = Resume.builder().resumeId(resumeId).build();

        Status status = Status.builder()
                .statPostId(post)
                .statResumeId(resume)
                .statShow(false)
                .build();

        return status;
    }

    default ApplyListDTO entityToApplyListDTO(Status status) {
        Post post = status.getStatPostId();
        Long postId = null;
        String postTitle = null;
        String comName = null;

        if (post != null) {
            postId = post.getPostId();
            postTitle = post.getPostTitle();
            comName = post.getPostComName();
        } else {
            postId = 0L;
            postTitle = "탈퇴한 기업의 공고입니다.";
            comName = "탈퇴한 기업";
        }

        ApplyListDTO applyListDTO = ApplyListDTO.builder()
                .statId(status.getStatId())
                .statPostId(postId)
                .statResumeId(status.getStatResumeId().getResumeId())
                .statPass(status.getStatPass())
                .statApplyDate(status.getStatApplyDate())
                .postName(postTitle)
                .companyName(comName)
                .resumeTitle(status.getStatResumeId().getResumeTitle())
                .build();

        return applyListDTO;
    }

    default ApplierListDTO entityToApplierListDTO(Status status) {
        ApplierListDTO applierListDTO = ApplierListDTO.builder()
                .statId(status.getStatId())
                .statPostId(status.getStatPostId().getPostId())
                .statResumeId(status.getStatResumeId().getResumeId())
                .statPass(status.getStatPass())
                .statApplyDate(status.getStatApplyDate())
                .applierName(status.getStatResumeId().getResumeMemName())
                .postTitle(status.getStatPostId().getPostTitle())
                .postOccupation(status.getStatPostId().getPostOccCode().getOccName())
                .postEndDate(status.getStatPostId().getPostEndDate())
                .build();

        return applierListDTO;
    }

    default SendMailDTO entityToSendMailDTO(Status status) {
        SendMailDTO sendMailDTO = SendMailDTO.builder()
                .statPass(status.getStatPass())
                .applierEmail(status.getStatResumeId().getResumeMemEmail())
                .applierName(status.getStatResumeId().getResumeMemName())
                .companyName(status.getStatPostId().getPostComId().getComName())
                .postName(status.getStatPostId().getPostTitle())
                .build();

        return sendMailDTO;
    }

    default IntervieweeListDTO entityToIntervieweeListDTO(Status status) {
        IntervieweeListDTO intervieweeListDTO = IntervieweeListDTO.builder()
                .applierName(status.getStatResumeId().getResumeMemName())
                .statPass(status.getStatPass())
                .statId(status.getStatId())
                .postOccupation(status.getStatPostId().getPostOccCode().getOccName())
                .interviewPlace(status.getStatInterviewPlace())
                .interviewDate(status.getStatInterviewDate())
                .build();

        return intervieweeListDTO;
    }

    default SendMailToIntervieweeDTO entityToSendMailToIntervieweeDTO(Status status) {
        SendMailToIntervieweeDTO sendMailToIntervieweeDTO = SendMailToIntervieweeDTO.builder()
                .applierEmail(status.getStatResumeId().getResumeMemEmail())
                .applierName(status.getStatResumeId().getResumeMemName())
                .companyName(status.getStatPostId().getPostComId().getComName())
                .postName(status.getStatPostId().getPostTitle())
                .interviewDate(status.getStatInterviewDate())
                .interviewPlace(status.getStatInterviewPlace())
                .build();

        return sendMailToIntervieweeDTO;
    }
}
