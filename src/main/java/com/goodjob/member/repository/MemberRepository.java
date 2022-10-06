package com.goodjob.member.repository;

import com.goodjob.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 김도현 22.9.29 작성
 **/
public interface MemberRepository extends JpaRepository<Member, Long> {
    Long countByMemLoginId(String memLoginId);

   Optional<Member> findByMemLoginId(String memLoginId);


}
