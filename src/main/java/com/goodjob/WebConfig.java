package com.goodjob;

import com.goodjob.admin.interceptor.AdminLoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 10.5 인터셉터 설정 클래스. By.OH
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    //인터셉터 추가 메소드
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new AdminLoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/admin/**") // 인터셉터를 적용할 URI
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/admin/login","/assets/**","/*.css","/*.js"); // 제외 URI

    }
}
