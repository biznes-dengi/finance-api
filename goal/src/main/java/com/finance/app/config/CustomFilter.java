package com.finance.app.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class CustomFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        log.info("---- REQUEST -----");

        final var fpp = httpServletRequest.getHeaderNames();
        while (fpp.hasMoreElements()) {
            String headerName = fpp.nextElement();
            String headerValue = httpServletRequest.getHeader(headerName);
            log.info("headerName: {} headerValue: {}", headerName, headerValue);
        }

        log.info("---- RESPONSE -----");

        final var loo = (List<String>) httpServletResponse.getHeaderNames();

        loo.forEach(it -> {
            String headerValue = httpServletResponse.getHeader(it);
            log.info("headerName: {} headerValue: {}", it, headerValue);
        });


        filterChain.doFilter(servletRequest, servletResponse);
    }
}