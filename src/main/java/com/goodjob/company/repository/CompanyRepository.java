/**
 * 2022.10.05 - HO
 * 회원가입시 아이디 중복확인 시도중(checkId 메서드)
 * ->2022.10.06 checkId2 메서드로 구현.
 *
 * +2022.10.18 -HO
 * 기업회원정보 수정하기 - updateInfo()
 */
package com.goodjob.company.repository;

import com.goodjob.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    @Modifying
    @Transactional
    @Query("update Company c set c.comPw =:comPw where c.comLoginId =:comLoginId")
    void updatePassword(String comPw, String comLoginId);

    Long countByComNameAndComEmail(String comName, String comEmail);

    Optional<Company> findByComNameAndComEmail(String comName, String comEmail);

    //아이디 존재여부 확인
    Long countByComName(String comName);  //주의 countBy메서드 반환타입은 Long!

    //아이디 찾기
    Optional<Company> findByComName(String comName);

    @Query("select count(c) from Company c where c.comLoginId =:comLoginId")
    int checkId2(@Param("comLoginId") String comLoginId);

    //추가
    Optional<Company> findByComLoginId(String comLoginId);

    //22.10.18 기업회원정보 수정하기
    @Transactional
    @Modifying
    @Query("update Company c set c.comName =:#{#c.comName}, c.comBusiNum =:#{#c.comBusiNum}, c.comPhone =:#{#c.comPhone}," +
            "c.comComdivCode.comdivCode =:#{#c.comComdivCode.comdivCode}, c.comEmail =:#{#c.comEmail}," +
            " c.comAddress =:#{#c.comAddress} where c.comLoginId =:#{#c.comLoginId}")
    int updateInfo(@Param("c") Company company);

    @Modifying
    @Transactional
    @Query("update Company c set c.comName =:comName where c.comLoginId =:comLoginId")
    void updateInfo2(String comName, String comLoginId);

    /**
     * 김도현 22.10.29 작성
     * 비밀번호 찾기에 사용되는 메소드
     **/
    Company findByComEmail(String comEmail);

}
