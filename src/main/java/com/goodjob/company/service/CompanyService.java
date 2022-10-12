/**
 * HO - 2022.10.05
 * 서비스 클래스
 * 기업회원 회원가입 메서드
 * + 2022.10.06 회원가입시 아이디 중복확인 메서드
 */
package com.goodjob.company.service;

import com.goodjob.company.Company;
import com.goodjob.company.repository.CompanyRepository;
import com.goodjob.company.dto.CompanyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    //기업회원가입정보 DB에 저장하는 메서드
    @Transactional
    public Long createCompanyUser(CompanyDTO companyDTO) {
        Company newCompanyUser = companyDTO.toEntity();
        companyRepository.save(newCompanyUser);
        return newCompanyUser.getComId();
    }

    //아이디 중복확인 메서드
    public int checkId2(String comLoginId) throws Exception {
        return companyRepository.checkId2(comLoginId);
    }
    public Optional<Company> loginIdCheck(String comLoginId) {
        return companyRepository.findByComLoginId(comLoginId);

    }



}
