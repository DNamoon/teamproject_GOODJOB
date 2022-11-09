package com.goodjob.career.service;

import com.goodjob.career.Career;
import com.goodjob.career.dto.CareerDTO;
import com.goodjob.resume.Resume;

import java.util.List;

/**
 * 박채원 22.10.03 작성
 */

public interface CareerService {

    void registerCareerInfo(List<CareerDTO> careerDTO);
    List<CareerDTO> bringCareerInfo(Long resumeId);
    Long addNullCareerInfo(Long resumeId);
    void deleteCareerList(Long careerId);
    default Career dtoToEntity(CareerDTO careerDTO){
        Resume resume = Resume.builder().resumeId(careerDTO.getResumeId()).build();

        Career career = Career.builder()
                .careerId(careerDTO.getCareerId())
                .careerCompanyName(careerDTO.getCareerCompanyName())
                .careerJoinedDate(careerDTO.getCareerJoinedDate())
                .careerRetireDate(careerDTO.getCareerRetireDate())
                .careerTask(careerDTO.getCareerTask())
                .resume(resume)
                .build();

        return career;
    }

    default CareerDTO entityToDTO(Career career){
        CareerDTO careerDTO = CareerDTO.builder()
                .careerId(career.getCareerId())
                .careerCompanyName(career.getCareerCompanyName())
                .careerJoinedDate(career.getCareerJoinedDate())
                .careerRetireDate(career.getCareerRetireDate())
                .careerTask(career.getCareerTask())
                .resumeId(career.getResume().getResumeId())
                .build();

        return careerDTO;
    }
}
