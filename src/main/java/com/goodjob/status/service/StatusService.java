package com.goodjob.status.service;

import com.goodjob.post.Post;
import com.goodjob.post.postdto.PageRequestDTO;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.resume.Resume;
import com.goodjob.status.Status;
import com.goodjob.status.dto.ApplierListDTO;
import com.goodjob.status.dto.ApplyDTO;
import com.goodjob.status.dto.ApplyListDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 박채원 22.10.26 작성
 */

public interface StatusService {
    void applyResume(Long postId, Long resumeId);
    PageResultDTO<ApplyListDTO, Status> getApplyList(String loginId, int pageNum);
    PageResultDTO<ApplierListDTO, Status> getApplierList(String loginId, int pageNum);

    default Status dtoToEntity(Long postId, Long resumeId){
        Post post = Post.builder().postId(postId).build();
        Resume resume = Resume.builder().resumeId(resumeId).build();

        Status status = Status.builder()
                .statPostId(post)
                .statResumeId(resume)
                .build();

        return status;
    }

    default ApplyListDTO entityToListDTO(Status status){
        ApplyListDTO applyListDTO = ApplyListDTO.builder()
                .statId(status.getStatId())
                .statPostId(status.getStatPostId().getPostId())
                .statResumeId(status.getStatResumeId().getResumeId())
                .statApplyDate(status.getStatApplyDate())
                .postName(status.getStatPostId().getPostTitle())
                .companyName(status.getStatPostId().getPostComId().getComName())
                .resumeTitle(status.getStatResumeId().getResumeTitle())
                .build();

        return applyListDTO;
    }
}
