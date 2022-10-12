package com.goodjob;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * 김도현 22.10.01 작성
 * 김도현 22.10.07 수정 (로그인 에러로 filterChain 메소드 수정)
 */
@Configuration
public class SecurityConfig {


    //  비밀번호 암호화 (단방향)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable()            //cors 방지
                .csrf().disable()            //csrf 방지
//                .formLogin().loginPage("/auth/member/login").and()		//기본 로그인페이지 없애기
//                             .defaultSuccessUrl("/").and()
                .authorizeRequests()
                .antMatchers("/com/**","/auth/**", "/", "/resume/**", "/admin/**").permitAll() // /auth이하의 주소들은 인증 필요x
                .antMatchers("/css/**", "/js/**").permitAll()
//                .antMatchers("/auth/member/login").isAnonymous()
                .anyRequest().authenticated().and()// 이외 모든 요청은 인증 시 접근 가능
                .headers().frameOptions().disable();

        return http.build();
    }
}