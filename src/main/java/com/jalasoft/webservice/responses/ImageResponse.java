/*
 * Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */

package com.jalasoft.webservice.responses;

/**
 * Image Response class.
 */
public class ImageResponse extends SuccessResponse {
    private String urlPreview;
    private String urlDownload;

    /**
     * Image Response Constructor
     *
     * @param name
     * @param status
     * @param detail
     */
    public ImageResponse(String name, Integer status, String detail){
        super(name, status, detail);
    }

    /**
     * Get url
     * @return
     */
    public String getUrlPreview() {
        return urlPreview;
    }

    /**
     * Set url value
     * @param url
     */
    public void setUrlPreview(String url) {
        this.urlPreview = url;
    }

    /***
     * Get urlDownload value
     * @return
     */
    public String getUrlDownload() {
        return urlDownload;
    }

    /***
     * Set urlDownload value
     * @param urlDownload
     */
    public void setUrlDownload(String urlDownload) {
        this.urlDownload = urlDownload;
    }
}
