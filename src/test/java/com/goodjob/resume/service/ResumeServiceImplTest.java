package com.goodjob.resume.service;

import com.goodjob.resume.repository.ResumeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ResumeServiceImplTest {

    @Autowired
    private ResumeRepository resumeRepository;

    @Test
    void deleteResume() {
        resumeRepository.deleteById(353L);
    }
}