/*
 * Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */

package com.jalasoft.webservice.controller.filters;

import com.jalasoft.webservice.entitities.Cache;
import com.jalasoft.webservice.responses.ErrorResponse;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns={"/*"}) ///manejar filtros
public class TokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    //Lis<use>...

    /***
     * chain: sirve para continuar
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (Cache.getInstance().isValid(req.getHeader("authorization"))) {
            chain.doFilter(request, response);//continuar
        }
        else{
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");//clasico retorno
        }
    }

    @Override
    public void destroy() {

    }
}
