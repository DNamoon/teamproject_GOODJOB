package com.goodjob.member;



/**
 * 김도현 22.10.05 작성
 * Spring Security를 이용해 로그인/로그아웃/인증/인가 등을 처리하기 위한 설정 파일
 **/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig  {


   //  비밀번호 암호화 (단방향)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 10.6 시큐리티가 막지 않아야하는 URI까지 적용.
     * 추후 논의 필요 By.OH
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable()			//cors 방지
                .csrf().disable()			//csrf 방지
                .formLogin().loginPage("/auth/member/login")		//기본 로그인페이지 없애기
                .defaultSuccessUrl("/").and()
                .authorizeRequests()
                .antMatchers("/auth/**","/","/resume/**","/admin/**","/**","/*","/assets/css/nucleo-svg.css").permitAll() // /auth이하의 주소들은 인증 필요x
                .antMatchers("/css/**","/js/**","/fonts/**","https/**","/img/**","/assets/**","/comIdCheck","/assets/css/**").permitAll()
                .anyRequest().authenticated().and()
                .headers().frameOptions().disable();

        return http.build();
    }
}
