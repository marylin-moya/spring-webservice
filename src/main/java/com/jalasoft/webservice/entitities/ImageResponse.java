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
 * Image Response class.
 */
public class ImageResponse extends Response{

    /**
     * Image Response Constructor
     *
     * @param name
     * @param status
     * @param detail
     */
    public ImageResponse(String name, Integer status, String detail) {
        super(name, status, detail);
    }
}
