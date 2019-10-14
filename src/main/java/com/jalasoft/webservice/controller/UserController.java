/*
 * Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */

package com.jalasoft.webservice.controller;

import com.jalasoft.webservice.entitities.ErrorResponse;
import com.jalasoft.webservice.entitities.Response;
import com.jalasoft.webservice.entitities.User;
import com.jalasoft.webservice.model.DBManager;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.jalasoft.webservice.utils.Constants.USER_PATH;

@RestController
@RequestMapping(USER_PATH)
public class UserController {
    @PostMapping(value = "/login", consumes = {"multipart/form-data"})
    public Response validate(@Valid @NotNull @NotBlank @RequestParam("userName") String userName,
                                         @Valid @NotNull @NotBlank @RequestParam("password") String password){
        User user = DBManager.getUser(userName, password);
        if(user == null){
            return new ErrorResponse(HttpStatus.BAD_REQUEST.name(),
                    HttpStatus.BAD_REQUEST.value(), "no user");
        }

        String key = "dev-fund2";
        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .setSubject("admin")
                .claim("role", "administrator")
                .claim("email", "user@gmail.com")
                .compact();


        return new Response(HttpStatus.ACCEPTED.name(),
                HttpStatus.ACCEPTED.value(), String.format("token: %s", token));
    }
}
