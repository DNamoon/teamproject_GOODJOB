/**
 * 2022.10.05 - HO
 * 회원가입시 아이디 중복확인 시도중(checkId 메서드)
 * ->2022.10.06 checkId2 메서드로 구현.
 */
package com.goodjob.company.repository;

import com.goodjob.company.Company;
import com.goodjob.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    @Query("select count(c) from Company c where c.comLoginId =:comLoginId")
    int checkId2(@Param("comLoginId") String comLoginId);

    //추가
    Optional<Company> findByComLoginId(String comLoginId);
}