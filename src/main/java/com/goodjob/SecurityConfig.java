package com.goodjob;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * 김도현 22.10.01 작성
 * 김도현 22.10.07 수정 (로그인 에러로 filterChain 메소드 수정)
 * 오성훈 22.10.19 수정 SecurityFilterChain에 .antMatchers url추가.(/Users/**)
 * 오성훈 22.11.01 수정 SecurityFilterChain에 .sessionManagement( )추가
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
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //Security가 세션을 생성 못하게 막음. 기존엔 항시 생성
                .and()
                .authorizeRequests()
                .antMatchers("/member/**","/","/resume/**","/admin/**","/com/**","/Users/**","/login","/getPagingPostList","/search","/home","/post/**","/img/**","/status/**","/customerInquiry/**").permitAll() // /auth이하의 주소들은 인증 필요x
                .antMatchers("/css/**","/js/**").permitAll()
                .anyRequest().authenticated().and()
                .headers().frameOptions().disable();
        return http.build();
    }

}
