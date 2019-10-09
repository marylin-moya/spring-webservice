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

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate;
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

    @Override
    public void Validate() throws ParamsInvalidException {
        if(this.path.isEmpty()) {
            throw new ParamsInvalidException(10, "filePath");
        }
        if(this.lang.isEmpty()) {
            throw new ParamsInvalidException(11, "Language");
        }
        if(this.path == null) {
            throw new ParamsInvalidException(11, "filePath null");
        }
        if(this.checkSum.isEmpty()) {
            throw new ParamsInvalidException(10, "checksum");
        }
        if(this.checkSum == null) {
            throw new ParamsInvalidException(10, "checksum 111");
        }
    }
}
