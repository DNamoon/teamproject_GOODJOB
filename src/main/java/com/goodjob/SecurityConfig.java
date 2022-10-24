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
public class SecurityConfig  {


    //  비밀번호 암호화 (단방향)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable()			//cors 방지
                .csrf().disable()			//csrf 방지
                //                .formLogin().loginPage("/auth/member/login")		//기본 로그인페이지 없애기
                //                .defaultSuccessUrl("/").and()
                .authorizeRequests()
                .antMatchers("/member/**","/","/resume/**","/admin/**","/com/**","/p/**", "/o/**").permitAll() // /auth이하의 주소들은 인증 필요x
                .antMatchers("/css/**","/js/**").permitAll()
                .anyRequest().authenticated().and()
                .headers().frameOptions().disable();
        return http.build();
    }
}
