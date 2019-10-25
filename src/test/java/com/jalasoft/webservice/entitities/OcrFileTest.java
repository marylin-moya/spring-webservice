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
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test Cases related to OcrFile
 */
public class OcrFileTest {
    @Test(expected=ParamsInvalidException.class)
    public void ExceptionIsRaisedWhenLangIsNull() throws ParamsInvalidException {
        OcrFile orc = new OcrFile();
        orc.setFileName("test");
        orc.setPath("test");
        orc.setCheckSum("sfdsf");
        orc.setFullFilePath("er");
        orc.setLang(null);
        orc.validateContextOcr();
    }

    @Test(expected=ParamsInvalidException.class)
    public void ExceptionIsRaisedWhenLangIsEmpty() throws ParamsInvalidException {
        OcrFile orc = new OcrFile();
        orc.setFileName("test");
        orc.setPath("test");
        orc.setCheckSum("sfdsf");
        orc.setFullFilePath("er");
        orc.setLang("");
        orc.validateContextOcr();
    }
}