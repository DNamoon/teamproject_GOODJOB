package com.goodjob.status.service;

import com.goodjob.post.postdto.PageRequestDTO;
import com.goodjob.post.postdto.PageResultDTO;
import com.goodjob.status.Status;
import com.goodjob.status.dto.ApplyListDTO;
import com.goodjob.status.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public List<ApplyListDTO> getApplyList(String loginId, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum,10);
        Page<Status> applyList = statusRepository.getStatusByStatResumeId_ResumeMemId_MemLoginIdOrderByStatApplyDateDesc(loginId, pageable);
        System.out.println("//////////////////////////////////////");
        System.out.println(applyList.getTotalPages());
        System.out.println(applyList.getTotalElements());
        System.out.println(applyList.getNumber());
        System.out.println(applyList.hasNext());
        return applyList.stream().map(apply -> entityToListDTO(apply)).collect(Collectors.toList());
    }

    @Override
    public PageResultDTO<ApplyListDTO, Status> getList(String loginId, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum,10);
        Page<Status> applyList = statusRepository.getStatusByStatResumeId_ResumeMemId_MemLoginIdOrderByStatApplyDateDesc(loginId, pageable);
        Function<Status, ApplyListDTO> fn = (entity -> entityToListDTO(entity));
        return new PageResultDTO(applyList, fn);
    }

}
