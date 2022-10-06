package com.goodjob.member;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
public class PwEncoderTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @DisplayName("PasswordEncoder 테스트")
    @Test
    void testPasswordEncoder() {
        String plainText = "test11";
        String encodedPassword = passwordEncoder.encode(plainText);
        log.info("평문 패스워드: {}", plainText);
        log.info("암호화된 패스워드: {}", encodedPassword);
        assertTrue(passwordEncoder.matches(plainText, encodedPassword));
    }

}
