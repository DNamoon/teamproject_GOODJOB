package com.goodjob.company.service;

import com.goodjob.company.Company;
import com.goodjob.company.CompanyRepository;
import com.goodjob.company.dto.CompanyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Transactional
    public Long createCompanyUser(CompanyDTO companyDTO) {
        Company newCompanyUser = companyDTO.toEntity();
        companyRepository.save(newCompanyUser);
        return newCompanyUser.getComId();
    }

    public String checkId(String id, String type) {
        if(type.equals("user")) {
            return companyRepository.checkId(id);
        }else {
            return null;
        }

    }



}
