package com.goodjob.career.service;

import com.goodjob.career.Career;
import com.goodjob.career.dto.CareerDTO;
import com.goodjob.career.repository.CareerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * 박채원 22.10.03 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class CareerServiceImpl implements CareerService {

    private final CareerRepository careerRepository;

    @Override
    public void registerCareerInfo(CareerDTO careerDTO) {
        log.info("=========== 이력서 경력사항 등록 ===========");
        Career career = dtoToEntity(careerDTO);

        careerRepository.save(career);
    }

    @Override
    public void existOrNotResumeId(CareerDTO careerDTO) {
        if(careerRepository.findByResumeId(careerDTO.getResumeId()) == null){
            Career career = dtoToEntity(careerDTO);
            careerRepository.save(career);
        }
    }
}
