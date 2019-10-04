/*
 * Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */

package com.jalasoft.webservice.entitities;

/**
 * Error Response Class
 */
public class ErrorResponse extends Response {
    /**
     * Response Constructor
     *
     * @param name   Status in String Format.
     * @param status Status in Numeric Format.
     * @param detail Message String.
     */
    public ErrorResponse(String name, Integer status, String detail) {
        super(name, status, detail);
    }
}
