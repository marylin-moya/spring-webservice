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

/***
 * Class to Verify if a value is empty or null
 */
public class NullOrEmptyValidation implements IValidateStrategy {
    private String value;

    public NullOrEmptyValidation(String value) {
        this.value = value;
    }

    @Override
    public void validate() throws ParamsInvalidException {
        if(null == this.value || this.value.isEmpty())
            throw new ParamsInvalidException(11, "Field is empty or null");
    }
}
