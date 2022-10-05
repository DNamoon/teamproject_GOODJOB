package com.goodjob.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 박채원 22.10.02 작성
 */

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.memLoginId =:loginId")
    Member findLoginInfo(String loginId);

}
