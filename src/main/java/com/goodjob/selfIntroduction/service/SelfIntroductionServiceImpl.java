package com.goodjob.selfIntroduction.service;

import com.goodjob.selfIntroduction.SelfIntroduction;
import com.goodjob.selfIntroduction.dto.SelfIntroductionDTO;
import com.goodjob.selfIntroduction.repository.SelfIntroductionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * 박채원 22.10.03 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class SelfIntroductionServiceImpl implements SelfIntroductionService {

    private final SelfIntroductionRepository selfIntroductionRepository;

    @Override
    public void registerSelfInfo(SelfIntroductionDTO selfIntroductionDTO) {
        SelfIntroduction selfIntroduction = dtoToEntity(selfIntroductionDTO);

        if(selfIntroductionRepository.findByResume_ResumeId(selfIntroductionDTO.getResumeId()) == null){
            log.info("=========== 이력서 대외활동 및 자소서항목 등록 ===========");
            selfIntroductionRepository.save(selfIntroduction);
        }else{
            log.info("=========== 이력서 대외활동 및 자소서항목 수정 ===========");
            selfIntroductionRepository.updateSelfIntroInfo(selfIntroduction.getSelfInterActivity(),selfIntroduction.getSelfLetter(),selfIntroduction.getResume().getResumeId());
        }

    }

    @Override
    public int existOrNotResumeId(Long resumeId) {
        if(selfIntroductionRepository.findByResume_ResumeId(resumeId) == null){
            return 0;
        }
        return 1;
    }

    @Override
    public SelfIntroductionDTO bringSelfIntroInfo(Long resumeId) {
        SelfIntroduction selfIntroduction = selfIntroductionRepository.findByResume_ResumeId(resumeId);
        SelfIntroductionDTO selfIntroductionDTO = entityToDTO(selfIntroduction);
        return selfIntroductionDTO;
    }
}
