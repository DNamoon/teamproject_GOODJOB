package com.goodjob.career.service;

import com.goodjob.career.Career;
import com.goodjob.career.dto.CareerDTO;
import com.goodjob.career.repository.CareerRepository;
import com.goodjob.certification.Certification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 박채원 22.10.03 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class CareerServiceImpl implements CareerService {

    private final CareerRepository careerRepository;

    @Override
    public void registerCareerInfo(List<CareerDTO> list) {
        if (careerRepository.findByResumeId(list.get(0).getResumeId()) == null) {
            for (int i = 0; i < list.size(); i++) {
                Career career = dtoToEntity(list.get(i));
                log.info("=========== 이력서 경력사항 등록 ===========");
                careerRepository.save(career);
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                Career career = dtoToEntity(list.get(i));
                log.info("=========== 이력서 경력사항 수정 ===========");
                careerRepository.updateCareerInfo(career.getCareerCompanyName(), career.getCareerJoinedDate(), career.getCareerRetireDate(), career.getCareerTask(), career.getResume().getResumeId());
            }
        }
    }

    @Override
    public CareerDTO bringCareerInfo(Long resumeId) {
        Career career = careerRepository.findCareerInfoByResumeId(resumeId);
        CareerDTO careerDTO = entityToDTO(career);
        return careerDTO;
    }
}
