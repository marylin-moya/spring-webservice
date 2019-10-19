package com.jalasoft.webservice.controller;

import com.jalasoft.webservice.entitities.Cache;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jalasoft.webservice.utils.Constants.AUTHORIZATION;
import static com.jalasoft.webservice.utils.Constants.DOWNLOAD_PATH;
import static com.jalasoft.webservice.utils.Constants.LOGIN_PATH;

@WebFilter(urlPatterns = {"/*"})
public class TokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String url = req.getRequestURL().toString();
        String token = req.getHeader(AUTHORIZATION);

        if (url.contains(LOGIN_PATH) || url.contains(DOWNLOAD_PATH) || Cache.getInstance().isValid(token)) {
            chain.doFilter(request, response);
        } else {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
        }
    }

    @Override
    public void destroy() {

    }
}
