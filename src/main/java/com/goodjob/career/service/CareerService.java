package com.goodjob.career.service;


import com.goodjob.career.Career;
import com.goodjob.career.dto.CareerDTO;
import com.goodjob.resume.Resume;

/**
 * 박채원 22.10.03 작성
 */

public interface CareerService {

    void registerCareerInfo(CareerDTO careerDTO);

    void existOrNotResumeId(CareerDTO careerDTO);

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

    default CareerDTO entityToDTO(Career career, Resume resume){
        CareerDTO careerDTO = CareerDTO.builder()
                .careerId(career.getCareerId())
                .careerCompanyName(career.getCareerCompanyName())
                .careerJoinedDate(career.getCareerJoinedDate())
                .careerRetireDate(career.getCareerRetireDate())
                .careerTask(career.getCareerTask())
                .resumeId(resume.getResumeId())
                .build();

        return careerDTO;
    }
}
