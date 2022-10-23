package com.goodjob.resume.service;

import com.goodjob.member.Member;
import com.goodjob.resume.Resume;
import com.goodjob.resume.repository.ResumeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResumeServiceImplTest {

    @Autowired
    private ResumeRepository resumeRepository;

    @Test
    void deleteResume() {
        List<Resume> resume = resumeRepository.getResumeByResumeMemIdOrderByResumeId(Member.builder().memId(5L).build());
        System.out.println(resume);
//        resumeRepository.deleteById(355L);
    }
}