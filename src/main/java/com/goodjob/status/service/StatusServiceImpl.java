package com.goodjob.status.service;

import com.goodjob.status.Status;
import com.goodjob.status.dto.ApplyDTO;
import com.goodjob.status.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService{

    private final StatusRepository statusRepository;

    @Override
    public void applyResume(Long postId, Long resumeId) {
        log.info("=========== 지원완료 ===========");
        statusRepository.save(dtoToEntity(postId, resumeId));
    }

    @Override
    public List<ApplyDTO> getApplyList(String loginId) {
        List<Status> applyList = StatusRepository.getStatusByStatResumeId_ResumeMemId_MemLoginIdOOrderByStatApplyDateDesc(loginId);
        return null;
    }
}
