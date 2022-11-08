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

    @Test
    @Commit
    void saveCompany() {
        IntStream.rangeClosed(1, 20).forEach(i -> {

//            Company company = new Company(new Region("02", "서울특별시"), new Comdiv("Foreign", "외국계기업")
//                    , "test" + i, "1234");
//            companyRepository.save(company);
        });
    }

}