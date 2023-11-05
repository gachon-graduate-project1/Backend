package homemate.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 로그인 체크용 인터셉터
 */
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 세션에서 관리자 정보 조회
        HttpSession session = request.getSession();
        Object loginAdmin = session.getAttribute("loginAdmin");

        // 2. 관리자 정보 체크
        if (loginAdmin == null) {
            response.sendRedirect("/login");
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}
