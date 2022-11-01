package com.goodjob.status.service;

import com.goodjob.post.Post;
import com.goodjob.resume.Resume;
import com.goodjob.status.Status;
import com.goodjob.status.dto.ApplyListDTO;
import com.goodjob.status.repository.StatusRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

class StatusServiceImplTest {

    @Autowired
    private StatusRepository statusRepository;

//    @Test
//    void getApplyList() {
//        String loginId = "member3921";
//        List<Status> applyList = statusRepository.getStatusByStatResumeId_ResumeMemId_MemLoginIdOrderByStatApplyDateDesc(loginId);
//        System.out.println(applyList);
//    }
//
//    @Test
//    void getApplyList2() {
//        List<Status> list = statusRepository.getStatusByStatResumeId(25L);
//        System.out.println(list);
//    }

    @Test
    void saveDummies(){
        Resume resume = Resume.builder().resumeId(25L).build();
        Post post = Post.builder().postId(3L).build();
        LongStream.rangeClosed(1,20).forEach(i ->{
            Status status = Status.builder()
                    .statResumeId(resume)
                    .statPostId(post)
                    .statPass("미정")
                    .build();
            statusRepository.save(status);
        });
    }
}