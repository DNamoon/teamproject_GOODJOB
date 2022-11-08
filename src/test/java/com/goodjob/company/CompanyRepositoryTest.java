/**
 * HO - 2022.10.06
 * 아이디 중복확인 레포지토리 테스트
 */
package com.goodjob.company;

import com.goodjob.company.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.stream.IntStream;

@SpringBootTest
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void loginIdDuplicated() {
        int t = companyRepository.checkId2("test5");
        System.out.println("t = " + t);
    }

}