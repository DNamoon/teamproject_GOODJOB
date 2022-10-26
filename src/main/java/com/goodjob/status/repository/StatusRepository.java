package com.goodjob.status.repository;

import com.goodjob.member.Member;
import com.goodjob.post.Post;
import com.goodjob.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 박채원 22.10.26 수정
 */

public interface StatusRepository extends JpaRepository<Status,Long> {

    boolean existsByStatResumeId_ResumeMemId(Member member);
    boolean existsByStatResumeId_ResumeMemIdAndStatPostId(Member member, Post post);

}
