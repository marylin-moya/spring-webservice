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

public class ImageResponse extends Response {
    private String url;
    public ImageResponse(String name, Integer status, String detail){
        super(name, status, detail);
    }

    /**
     * Get url
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url value
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
