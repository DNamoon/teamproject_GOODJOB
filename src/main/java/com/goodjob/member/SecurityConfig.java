package com.goodjob.member;



/* Spring Security를 이용해 로그인/로그아웃/인증/인가 등을 처리하기 위한 설정 파일
   @EnableWebSecurity가 붙어 있을 경우 Spring Security를 구성하는
   기본적인 Bean들을 자동으로 구성해줌
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig  {

//    // resource의 정적 리소스는 보안필터를 거치지 않게 설정
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring().antMatchers("/resources/**");
//    }

   //  비밀번호 암호화 (단방향)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable()			//cors 방지
                .csrf().disable()			//csrf 방지
                .formLogin().loginPage("/member/login")		//기본 로그인페이지 없애기
                .defaultSuccessUrl("/").and()
                .authorizeRequests()
                .antMatchers("/auth/**","/").permitAll() // /auth이하의 주소들은 인증 필요x
                .antMatchers("/css/**").permitAll()
                .anyRequest().authenticated().and()
                .headers().frameOptions().disable();

        return http.build();
    }
}
