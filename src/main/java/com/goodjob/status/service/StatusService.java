package com.goodjob.status.service;

import com.goodjob.post.Post;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.resume.Resume;
import com.goodjob.status.Status;
import com.goodjob.status.dto.ApplierListDTO;
import com.goodjob.status.dto.ApplyListDTO;
import com.goodjob.status.dto.SendMailDTO;

import java.util.Optional;

/**
 * 박채원 22.10.26 작성
 */

public interface StatusService {
    void applyResume(Long postId, Long resumeId);
    PageResultDTO<ApplyListDTO, Status> getApplyList(String loginId, int pageNum);
    PageResultDTO<ApplierListDTO, Status> getApplierList(String loginId, Long postId, int pageNum);
    void changePass(Long statId);
    void changeUnPass(Long statId);
    SendMailDTO getApplierToSendMail(Long statId);
    int havePass(String loginId);
    default Status dtoToEntity(Long postId, Long resumeId){
        Post post = Post.builder().postId(postId).build();
        Resume resume = Resume.builder().resumeId(resumeId).build();

        Status status = Status.builder()
                .statPostId(post)
                .statResumeId(resume)
                .statShow((short) 0)
                .build();

        return status;
    }

    default ApplyListDTO entityToApplyListDTO(Status status){
        ApplyListDTO applyListDTO = ApplyListDTO.builder()
                .statId(status.getStatId())
                .statPostId(status.getStatPostId().getPostId())
                .statResumeId(status.getStatResumeId().getResumeId())
                .statPass(status.getStatPass())
                .statApplyDate(status.getStatApplyDate())
                .postName(status.getStatPostId().getPostTitle())
                .companyName(status.getStatPostId().getPostComId().getComName())
                .resumeTitle(status.getStatResumeId().getResumeTitle())
                .build();

        return applyListDTO;
    }

    default ApplierListDTO entityToApplierListDTO(Status status){
        ApplierListDTO applierListDTO = ApplierListDTO.builder()
                .statId(status.getStatId())
                .statPostId(status.getStatPostId().getPostId())
                .statResumeId(status.getStatResumeId().getResumeId())
                .statPass(status.getStatPass())
                .statApplyDate(status.getStatApplyDate())
                .applierId(status.getStatResumeId().getResumeMemId().getMemLoginId())
                .applierName(status.getStatResumeId().getResumeMemId().getMemName())
                .postTitle(status.getStatPostId().getPostTitle())
                .postOccupation(status.getStatPostId().getPostOccCode().getOccName())
                .postEndDate(status.getStatPostId().getPostEndDate())
                .build();

        return applierListDTO;
    }

    default SendMailDTO entityToSendMailDTO(Status status){
        SendMailDTO sendMailDTO = SendMailDTO.builder()
                .statPass(status.getStatPass())
                .applierEmail(status.getStatResumeId().getResumeMemEmail())
                .applierName(status.getStatResumeId().getResumeMemId().getMemName())
                .companyName(status.getStatPostId().getPostComId().getComName())
                .postName(status.getStatPostId().getPostTitle())
                .build();

        return sendMailDTO;
    }
}
