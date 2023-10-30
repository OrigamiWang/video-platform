package szu.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import szu.common.model.LoginUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//public class LoginInterceptor implements HandlerInterceptor {
//    private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class.getName());
//
//    private static final String[] passUrl = {"login", "register"};
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
//        String url = String.valueOf(request.getRequestURL());
//        LOGGER.info("用户访问: " + url);
//        for (String s : passUrl) {
//            // 放行某些特定url
//            if (url.contains(s)) {
//                return true;
//            }
//        }
//        HttpSession session = request.getSession();
//        LoginUser user = (LoginUser) session.getAttribute("user");
//        if (user != null) {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest,
//                           HttpServletResponse httpServletResponse,
//                           Object o, ModelAndView modelAndView) throws Exception {
//        LOGGER.info("postHandle...");
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest,
//                                HttpServletResponse httpServletResponse,
//                                Object o, Exception e) throws Exception {
//        LOGGER.info("afterCompletion...");
//    }
//}
