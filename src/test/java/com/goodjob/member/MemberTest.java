package com.goodjob.member;

import com.goodjob.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.sql.Date;

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

    @Test
    void count(){
        Integer integer = memberRepository.countByMemGender("F");
        System.out.println("integer = " + integer);
    }
    @Test
    public String getInfo(HttpSession session, Model model){
        String id = (String)session.getAttribute("sessionId");
        
        return "member/myPageForm";
    }
}