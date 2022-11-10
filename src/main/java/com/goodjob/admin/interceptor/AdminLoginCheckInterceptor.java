package com.goodjob.admin.interceptor;

import com.goodjob.admin.AdminConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 10.4 세션에 관리자가 있는지 체크하는 인터셉터.
 * 인터셉터는 컨트롤러가 호출되기 직전에 요청에 대한 필터링을 한다고 생각하면 됨.
 * By.OH
 */
@Slf4j
public class AdminLoginCheckInterceptor implements HandlerInterceptor {

    // Http 요청 시 최초 실행됨
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        if (session == null || session.getAttribute(AdminConst.ADMIN) == null) {
            log.info("관리자가 아닙니다.");
            response.sendRedirect("/admin/login");
            return false;
        }
        return true;
    }

}
