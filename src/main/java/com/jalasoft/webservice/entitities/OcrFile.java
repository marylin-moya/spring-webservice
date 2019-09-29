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

/****
 * OcrFile : Class to convert image to text
 *  Version : 1.0
 *  Date: 9/19/2019
 */
public class OcrFile extends BaseFile {
    private String lang;

    public String getLang() {
        return lang;
    }

    /***
     * Method to set language
     * @param lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }
}
