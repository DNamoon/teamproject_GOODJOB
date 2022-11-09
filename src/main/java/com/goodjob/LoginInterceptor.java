package com.goodjob;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 클라이언트의 요청을 컨트롤러에 전달하기 전에 호출된다.
     * 여기서 false를 리턴하면 다음 내용(Controller)을 실행하지 않는다.
     **/

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        if (session.getAttribute("sessionId") == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }


}
