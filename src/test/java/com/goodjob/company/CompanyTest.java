/**
 * HO - 2022.10.06
 * 기업회원가입 DB 들어가는지 테스트
 */
package com.goodjob.company;

import com.goodjob.member.MemberType;
import com.goodjob.member.Memberdiv;
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

//    @Test
//    @Commit
//    void save() {
//        Company company = new Company(1L
//                , new Region(2L, "서울특별시")
//                , new Memberdiv(MemberType.COMPANY)
//                , new Comdiv("기업분류테스트1", "기업분류테스트1")
//                , "test1", "1234"
//                , "01012341234"
//                , "tert@test.com"
//                , "gg"
//                , "10200435045"
//                , "ddddddd"
//                , "0");
//        companyRepository.save(company);
//    }
}
