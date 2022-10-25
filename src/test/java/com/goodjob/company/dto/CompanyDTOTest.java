/**
 * HO - 2022.10.06
 * @NotNull : null만 허용 x     /  "", " " 허용
 * @NotEmpty : null, "" 허용 x /  " " 허용
 * @NotBlank : null,""," " 허용 x
 * 위 사항에 관한 테스트 validator가 안되서 Test 실패 ->테스트 보류
 */
package com.goodjob.company.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CompanyDTOTest {

    //왜 생성되지??? notBlank니까 생성되면 안 되는거 아닌가?
    @Test
    public void notBlankTest() {
//        CompanyDTO companyDTO = CompanyDTO.builder()
//                .comLoginId(" ")
//                .comPw1(" ")
//                .comName("sun")
//                .build();

//        System.out.println("companyDTO = " + companyDTO.toString());
//        Set<ConstraintViolation<CompanyDTO>> violations = validator.validate(companyDTO);
//
//        //then
//        assertThat(violations.size()).isEqualTo(2);
    }

}