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

package com.jalasoft.webservice.entitities;

import com.jalasoft.webservice.error_handler.ParamsInvalidException;

/**
 * User class.
 */
public class User {
    private String userName;
    private String password;
    private String role;
    private String email;

    /**
     * Get User Name.
     *
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set User Name.
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Get Password.
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set Password.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get Role.
     *
     * @return
     */
    public String getRole() {
        return role;
    }

    /**
     * Set Role.
     *
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Get email.
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email.
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Validate fields method.
     */
    public void validate() throws ParamsInvalidException {
        if (this.userName == null) {
            throw new ParamsInvalidException(10, "userName");
        }
        if (this.userName.isEmpty()) {
            throw new ParamsInvalidException(11, "userName");
        }
        if (this.password == null) {
            throw new ParamsInvalidException(10, "password");
        }
        if (this.password.isEmpty()) {
            throw new ParamsInvalidException(11, "password");
        }
        if (this.role == null) {
            throw new ParamsInvalidException(10, "role");
        }
        if (this.role.isEmpty()) {
            throw new ParamsInvalidException(11, "role");
        }
        if (this.email == null) {
            throw new ParamsInvalidException(10, "email");
        }
        if (this.email.isEmpty()) {
            throw new ParamsInvalidException(11, "email");
        }
    }

    /**
     * Validate fields method.
     */
    public void validateLogin() throws ParamsInvalidException {
        if (this.userName == null || this.userName.isEmpty() || this.password == null || this.password.isEmpty()) {
            throw new ParamsInvalidException(16, "userName and Password");
        }
    }
}
