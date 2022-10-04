package com.goodjob.status;

import com.goodjob.member.Member;
import com.goodjob.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status,Long> {

    boolean existsByStatResumeId_ResumeMemId(Member member);
    boolean existsByStatResumeId_ResumeMemIdAndStatPostId(Member member, Post post);
}
