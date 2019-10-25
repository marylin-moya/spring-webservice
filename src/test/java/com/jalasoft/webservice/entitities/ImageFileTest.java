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
 * Test Cases related to ImageFile
 */
public class ImageFileTest {
    @Test(expected= ParamsInvalidException.class)
    public void ExceptionIsRaisedWhenFileNameDoesNotHaveValidFormat() throws ParamsInvalidException {
        ImageFile img = new ImageFile();
        img.setFileName("testFile");
        img.setTargetType("pdf");
        img.setPath("path");
        img.setCheckSum("sfdsf");
        img.setFullFilePath("fullpath");
        img.validateContextImg();
    }

    @Test(expected=ParamsInvalidException.class)
    public void ExceptionIsRaisedWhenTargetTypeHasNotSupportedType() throws ParamsInvalidException {
        ImageFile img = new ImageFile();
        img.setFileName("testFile.jpg");
        img.setTargetType("pdf");
        img.setPath("path");
        img.setCheckSum("sfdsf");
        img.setFullFilePath("fullpath");
        img.validateContextImg();
    }
}