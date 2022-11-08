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
import java.time.LocalDate;
import java.util.stream.IntStream;

@Transactional
@SpringBootTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;

//    @Test
//    @Commit
//    void sava() {
//        IntStream.rangeClosed(11, 20).forEach(i -> {
//
//            Member member = new Member(
//                    "test"+i,
//                    "1234",
//                    "010-1234-1234",
//                    "test@com.com",
//                    "testuser",
//                    Date.valueOf(LocalDate.of(2002 - i, 1, 1)),
//                    "address@dsfsdf"
//                    , "여", "1");
//            memberRepository.save(member);
//        });
//    }

    @Test
    void count() {
        Integer integer = memberRepository.countByMemGender("F");
        System.out.println("integer = " + integer);
    }
    @Test
    public String getInfo(HttpSession session, Model model){
        String id = (String)session.getAttribute("sessionId");
        
        return "member/myPageForm";
    }

    @Test
    public void deleteMem(){
        String id = "aeoo1112";
        Member member = Member.builder().memLoginId(id).build();
        memberRepository.delete(member);
        System.out.println("삭제완료");
    }
}