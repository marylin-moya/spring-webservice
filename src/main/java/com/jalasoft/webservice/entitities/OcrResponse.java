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
 * OcrResponse class to store the result of extractor Model
 */
public class OcrResponse extends Response {
    private final String content;

    /**
     * OcrResponse Constructor
     *
     * @param content Text extracted from file.
     */
    public OcrResponse(String name, Integer status, String detail, String content) {
        super(name, status, detail);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
