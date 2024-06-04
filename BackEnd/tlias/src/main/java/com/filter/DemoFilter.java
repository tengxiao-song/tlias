package com.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

//@WebFilter(urlPatterns = "/*")
public class DemoFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("intercept request");
        System.out.println("Logic before access resource");
        filterChain.doFilter(servletRequest, servletResponse); // 放行
        System.out.println("logic after access resource");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
        Filter.super.destroy();
    }
}
