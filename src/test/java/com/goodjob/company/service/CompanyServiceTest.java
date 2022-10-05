package com.goodjob.company.service;

import com.goodjob.company.Comdiv;
import com.goodjob.company.Region;
import com.goodjob.company.dto.CompanyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Test
    public void comRegi() {
        CompanyDTO companyDTO = CompanyDTO.builder()
                .comName("moon")
                .comPw("1234")
                .comLoginId("test4")
               // .comComdivCode(new Comdiv("StartUp","스타트업"))
                .comEmail("moon@moon.com")
                .comPhone("010-9999-1111")
                .comTerms("1")
                .build();
        System.out.println("companyDTO= " + companyDTO.toString());
        Long c = companyService.createCompanyUser(companyDTO);
        System.out.println("c = " + c);

    }

}