package com.goodjob.company;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
public class CompanyTest {

    @Autowired
    CompanyRepository companyRepository;

    @Test
    @Commit
    void save() {
        Company company = new Company(1L
                , new Region(2L, "서울특별시")
                , "test1", "1234"
                , "01012341234"
                , "tert@test.com"
                , "gg"
                , "10200435045"
                , "ddddddd"
                , "0");
        companyRepository.save(company);
    }
}
