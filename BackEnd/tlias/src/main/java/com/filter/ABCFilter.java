package com.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

//@WebFilter(urlPatterns = "/*") // 如有多个filter，顺序按照字母顺序
public class ABCFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("ABC intercept request");
        filterChain.doFilter(servletRequest, servletResponse); // 放行
    }
}
