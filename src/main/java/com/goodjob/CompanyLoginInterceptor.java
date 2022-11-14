package com.goodjob;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CompanyLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (session == null || !session.getAttribute("Type").toString().equals("company") || session.getAttribute("sessionId") == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
