package com.goodjob.member.repository;

import com.goodjob.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

/**
 * 박채원 22.10.02 작성
 */

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.memLoginId =:loginId")
    Member findLoginInfo(String loginId);
    
    Integer countByMemGender(String memGender);


/**
 * 김도현 22.9.29 작성
 **/
    Long countByMemLoginId(String memLoginId);


   Member findByMemLoginId(String memLoginId);


}
