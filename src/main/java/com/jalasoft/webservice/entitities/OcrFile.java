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

import com.jalasoft.webservice.error_handler.ParamsInvalidException;

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

    /**
     * Validate fields method.
     */
    @Override
    public void validate() throws ParamsInvalidException {
        super.validate();
        if (this.lang == null) {
            throw new ParamsInvalidException(10, "lang");
        }
        if (this.lang.isEmpty()) {
            throw new ParamsInvalidException(11, "lang");
        }
    }
}
