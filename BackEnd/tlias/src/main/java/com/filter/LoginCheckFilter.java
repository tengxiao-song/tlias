package com.filter;

import com.alibaba.fastjson.JSONObject;
import com.pojo.Result;
import com.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

//@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(LoginCheckFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // Get the request URI
        String uri = req.getRequestURI();
        log.info("Request URI: " + uri);

        // If the request URI contains "login", then allow the request to pass through
        if (uri.contains("login")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // Get JWT
        String jwt = req.getHeader("token");

        // If JWT is invalid, return an error
        if (!StringUtils.hasLength(jwt)) {
            log.info("No token found");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            log.info("Invalid token");
            Result error = Result.error("NOT_LOGIN");
            String invalidToken = JSONObject.toJSONString(error);
            resp.getWriter().write(invalidToken);
            return;
        }

        // If JWT is valid, allow the request to pass through
        log.info("Valid token");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
