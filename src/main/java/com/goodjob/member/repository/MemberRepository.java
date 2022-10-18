package com.goodjob.member.repository;

import com.goodjob.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

/**
 * 박채원 22.10.02 작성
 */

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.memLoginId =:loginId")
    Member findLoginInfo(@Param("loginId") String loginId);
    
    Integer countByMemGender(String memGender);


/**
 * 김도현 22.9.29 작성
 **/
    Long countByMemLoginId(String memLoginId);

   Optional<Member> findByMemLoginId(String memLoginId);

   @Transactional
   @Modifying
   @Query("update Member m set m.memAddress =:#{#p.memAddress},m.memBirthDate =:#{#p.memBirthDate}" +
            ",m.memEmail =:#{#p.memEmail},m.memGender =:#{#p.memGender},m.memName =:#{#p.memName},m.memPhone =:#{#p.memPhone} where m.memId =:#{#p.memId}")
    void updateInfo(@Param("p") Member member);
}
