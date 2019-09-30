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

/**
 * Response class to store the rest api result.
 */
public class Response {
    private final String name;
    private final Integer status;
    private final String detail;

    /**
     * Response Constructor
     *
     * @param name    Status in String Format.
     * @param status  Status in Numeric Format.
     * @param detail  Message String.
     */
    public Response(String name, Integer status, String detail) {
        this.name = name;
        this.status = status;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }
}
