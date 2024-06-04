package com.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.pojo.Result;
import com.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override // This method is called before the controller method is called
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        System.out.println("preHandle...");

        // Get the request URI
        String uri = req.getRequestURI();
        log.info("Request URI: " + uri);

        // Get JWT
        String jwt = req.getHeader("token");

        // If JWT is invalid, return an error
        if (!StringUtils.hasLength(jwt)) {
            log.info("No token found");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return false;
        }
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            log.info("Invalid token");
            Result error = Result.error("NOT_LOGIN");
            String invalidToken = JSONObject.toJSONString(error);
            resp.getWriter().write(invalidToken);
            return false;
        }

        // If JWT is valid, allow the request to pass through
        log.info("Valid token");
        return true;
    }

    @Override // This method is called after the controller method is called
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle...");
    }

    @Override // This method is called after the response has been sent
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion...");
    }
}
