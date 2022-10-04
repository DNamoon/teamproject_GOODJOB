package com.goodjob.selfIntroduction.service;

import com.goodjob.selfIntroduction.SelfIntroduction;
import com.goodjob.selfIntroduction.dto.SelfIntroductionDTO;
import com.goodjob.selfIntroduction.repository.SelfIntroductionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 박채원 22.10.03 작성
 */

@Service
@RequiredArgsConstructor
public class SelfIntroductionServiceImpl implements SelfIntroductionService {

    private final SelfIntroductionRepository selfIntroductionRepository;

    @Override
    public void registerSelfInfo(SelfIntroductionDTO selfIntroductionDTO) {
        SelfIntroduction selfIntroduction = dtoToEntity(selfIntroductionDTO);

        selfIntroductionRepository.save(selfIntroduction);
    }
}
