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

public class RangeValidation implements IValidateStrategy {
    private int min;
    private int max;
    public RangeValidation(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void validate() throws ParamsInvalidException {
        if(this.min > this.max)
            throw new ParamsInvalidException(13, "Range not allowed");;
    }
}
