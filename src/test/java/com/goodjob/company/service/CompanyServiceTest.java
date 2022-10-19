/**
 * HO - 2022.10.05
 * 서비스 테스트
  */
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