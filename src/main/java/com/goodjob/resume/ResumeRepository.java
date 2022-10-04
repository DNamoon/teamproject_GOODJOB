package com.goodjob.resume;

import com.goodjob.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume,Long>{

    List<Resume> findByResumeMemId(Member member);
}
