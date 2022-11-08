package com.goodjob;

import com.goodjob.admin.interceptor.AdminLoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 10.5 인터셉터 설정 클래스. By.OH
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private String connectPath = "/file/**";
    private String resourcePath = "file:///"+"C:/goodjobimg"+"/";

    //인터셉터 추가 메소드
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new AdminLoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/admin/**") // 인터셉터를 적용할 URI
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/admin/login","/assets/**","/*.css","/*.js"); // 제외 URI

        registry.addInterceptor(new LoginInterceptor())
                .order(2)
                .addPathPatterns("/member/**")
                .excludePathPatterns("/css/**","/js/**","/login","/*.css","/*.js","/assets/**",
                        "/member/login","/member/signUp","/member/checkId","/member/signupEmail","/member/checkEmail"
                        ,"/member/sendPw");

    }
    // 썸머노트(텍스트 에디터)에 대한 요청 응답 핸들러
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(connectPath)
                .addResourceLocations(resourcePath);


    }
}
