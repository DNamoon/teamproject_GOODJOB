package com.goodjob.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Commit
    void sava() {
//        Memberdiv memberdiv = new Memberdiv(MemberType.COMPANY);
//        MemberType memberType = MemberType.valueOf("COMPANY");
//     Arrays.stream(MemberType.values()).iterator().forEachRemaining(i-> System.out.println(i));
//        System.out.println("memberType = " + memberType);
//        System.out.println(MemberType.COMPANY);
        Member member = new Member(1L,
                "test",
                "1234",
                "01012341234",
                "test@com.com",
                "testuser",
                Date.valueOf("1000-10-10"),
                "address"
                , "M", "1");
        memberRepository.save(member);
    }
}