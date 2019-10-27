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

import java.util.ArrayList;
import java.util.List;

/***
 *  Class to validate if language value is supported by applicatioin
 */
public class LangValidation implements IValidateStrategy {
    private String value;
    List<String> langs;

    public LangValidation(String value) {
        this.value = value;
        langs =  new ArrayList<>();
        langs.add("eng");
        langs.add("spa");
    }

    @Override
    public void validate() throws ParamsInvalidException {
        if(!langs.contains(this.value))
            throw new ParamsInvalidException(12, "Language is no supported");
    }
}
