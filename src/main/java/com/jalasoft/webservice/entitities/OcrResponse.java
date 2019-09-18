package com.jalasoft.webservice.entitities;

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

/**
 * OcrResponse class to store the result of extractor Model
 */
public class OcrResponse {
    private final String name;
    private final Integer status;
    private final String detail;
    private final String content;

    /**
     * OrcResponse Constructor
     *
     * @param name Status in String Format.
     * @param status Status in Numeric Format.
     * @param detail Message String.
     * @param content Text extracted from file.
     */
    public OcrResponse(String name, Integer status, String detail, String content) {
        this.name = name;
        this.status = status;
        this.detail = detail;
        this.content = content;
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

    public String getContent() {
        return content;
    }
}
