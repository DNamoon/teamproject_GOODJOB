/**
 * 2022.10.22 - HO
 * 회원가입시 아이디 중복확인 시도중(chekId 메서드)
 */
package com.goodjob.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {


    Optional<Company> findByComLoginId(String id);

    @Query("select c from Company c where c.comLoginId = :comLoginId")
    String checkId(@Param("comLoginId") String comLoginId);
}
