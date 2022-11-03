package com.goodjob;

import com.goodjob.admin.AdminConst;
import com.goodjob.member.memDTO.MemberDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

    public class LoginInterceptor implements HandlerInterceptor {
/** 클라이언트의 요청을 컨트롤러에 전달하기 전에 호출된다.
 여기서 false를 리턴하면 다음 내용(Controller)을 실행하지 않는다. **/
@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    HttpSession session = request.getSession();

    if (session.getAttribute("sessionId") == null) {
        response.sendRedirect("/login");
        return false;
    }
    return true;
}

    /**
     클라이언트의 요청을 처리한 뒤에 호출된다. 컨트롤러에서 예외가 발생되면 실행되지 않는다.
     */
        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                               ModelAndView modelAndView) throws Exception {
            // TODO Auto-generated method stub

        }

    /**
     클라이언트 요청을 마치고 클라이언트에서 뷰를 통해 응답을 전송한뒤 실행이 된다. 뷰를 생성할 때에 예외가 발생할 경우에도 실행이 된다.
     */
        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
                throws Exception {
            // TODO Auto-generated method stub

        }


}
