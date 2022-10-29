package com.goodjob.status.dto;

import com.goodjob.post.Post;
import com.goodjob.resume.Resume;
import com.goodjob.status.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ApplierListDTO {
    private Long statId;
    private Long statPostId;
    private Long statResumeId;
    private Date statApplyDate;
    private String resumeTitle;
    private String applierName;
    private String applierGender;
    private int applierAge;
    private String postTitle;
    private String postOccupation;

    public Status dtoToEntity(){
        Post post = Post.builder().postId(getStatPostId()).build();
        Resume resume = Resume.builder().resumeId(getStatResumeId()).build();

        Status status = Status.builder()
                .statId(getStatId())
                .statPostId(post)
                .statResumeId(resume)
                .statApplyDate(getStatApplyDate())
                .build();

        return status;
    }

    public ApplierListDTO entityToDTO(Status status){
        ApplierListDTO applierListDTO = ApplierListDTO.builder()
                .statId(status.getStatId())
                .statPostId(status.getStatPostId().getPostId())
                .statResumeId(status.getStatResumeId().getResumeId())
                .statApplyDate(status.getStatApplyDate())
                .resumeTitle(status.getStatResumeId().getResumeTitle())
                .applierName(status.getStatResumeId().getResumeMemId().getMemName())
                .applierGender(status.getStatResumeId().getResumeMemId().getMemGender())
                .applierAge(LocalDate.now().getYear() - status.getStatResumeId().getResumeMemId().getMemBirthDate().getYear())
                .postTitle(status.getStatPostId().getPostTitle())
                .postOccupation(status.getStatPostId().getPostOccCode().getOccName())
                .build();

        return applierListDTO;
    }
}
