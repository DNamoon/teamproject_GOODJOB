package com.goodjob.member;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Integer countByMemGender(String memGender);

}
