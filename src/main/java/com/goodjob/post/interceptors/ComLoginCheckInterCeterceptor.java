package com.goodjob.post.interceptors;

import com.goodjob.admin.AdminConst;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Log4j2
public class ComLoginCheckInterCeterceptor  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        if (session == null || !session.getAttribute("Type").equals("company")) {
            log.info("기업 회원이 아닙니다");
            response.sendRedirect("/login");
            return false;
        }


        return true;
    }
}
