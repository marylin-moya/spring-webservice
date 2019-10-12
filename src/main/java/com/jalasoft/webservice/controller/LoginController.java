/*
 *
 *  Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with Jalasoft.
 *
 */
package com.jalasoft.webservice.controller;

import com.jalasoft.webservice.entitities.User;
import com.jalasoft.webservice.error_handler.DatabaseException;
import com.jalasoft.webservice.model.DBManager;
import com.jalasoft.webservice.responses.ErrorResponse;
import com.jalasoft.webservice.responses.LoginResponse;
import com.jalasoft.webservice.responses.Response;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Login Controller class to implement Rest EndPoint to generate a token
 */
@RestController
public class LoginController {
    private static final String ROLE = "role";
    private static final String EMAIL = "email";

    @PostMapping(value = "/login")
    public Response validate(@RequestParam("user") String userName,
                             @RequestParam("password") String password) {

        User user = null;
        try {
            user = DBManager.getUser(userName, password);
        } catch (DatabaseException e) {
            return new ErrorResponse(HttpStatus.UNAUTHORIZED.name(),
                    HttpStatus.UNAUTHORIZED.value(),
                    e.getMessage());
        }

        String key = "dev-fun2";
        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .setSubject(user.getUserName())
                .claim(ROLE, user.getRole())
                .claim(EMAIL, user.getEmail())
                .compact();
        return new LoginResponse(HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                String.format("Token for %s user successfully generated", userName),
                token);
    }

    @PostMapping(value = "/user")
    public Response createUser(@RequestParam("userName") String userName,
                               @RequestParam("password") String password,
                               @RequestParam("role") String role,
                               @RequestParam("email") String email) {

        User user = new User();
        user.setRole(role);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserName(userName);
        try {
            DBManager.insertUser(user);
        } catch (DatabaseException e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST.name(),
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage());
        }
        return new Response(HttpStatus.OK.name(),
                HttpStatus.OK.value(),
                String.format("User %s successfully created", user.getUserName()));
    }
}
