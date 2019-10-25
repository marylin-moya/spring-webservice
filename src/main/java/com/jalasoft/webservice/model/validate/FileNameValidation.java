/*
 * Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */

package com.jalasoft.webservice.model.validate;

import com.jalasoft.webservice.error_handler.ParamsInvalidException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to Validate if a file name has a correct format
 */
public class FileNameValidation implements IValidateStrategy{
    private String value;
    public FileNameValidation(String value) {
        this.value = value;
    }

    @Override
    public void validate() throws ParamsInvalidException {
        Pattern fileExtnPtrn = Pattern.compile("([^\\s]+(\\.(?i)(jpg|png))$)");
        Matcher mtch = fileExtnPtrn.matcher(value);
        if(!mtch.matches()) {
            throw new ParamsInvalidException(15, "parameter is not image file");
        }
    }
}
