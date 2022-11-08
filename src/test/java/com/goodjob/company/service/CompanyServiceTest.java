/**
 * HO - 2022.10.05
 * 서비스 테스트
  */
package com.goodjob.company.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

//    @Test
//    public void comRegi() {
//        CompanyDTO companyDTO = CompanyDTO.builder()
//                .comName("moon")
//                .comPw1("1234")
//                .comLoginId("test4")
//               // .comComdivCode(new Comdiv("StartUp","스타트업"))
//                .comEmail("moon@moon.com")
//                .comPhone("010-9999-1111")
//                .comTerms("1")
//                .build();
//        System.out.println("companyDTO= " + companyDTO.toString());
//        Long c = companyService.createCompanyUser(companyDTO);
//        System.out.println("c = " + c);
//
//    }

    //아이디 중복체크 메서드 테스트
    @Test
    public void comLoginCheck() throws Exception {
        String id = "test1";
        String id2 = "test11322131";
        int i = companyService.checkId2(id);
        int i2 = companyService.checkId2(id2);

        System.out.println("i = " + i);
        System.out.println("i2 = " + i2);
    }

}