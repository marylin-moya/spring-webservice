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

/**
 * Class to Validate targetType
 */
public class TargetTypeValidation implements IValidateStrategy{
    private String value;
    private enum TypeSupported {
        PNG, JPG, BMP
    }

    /***
     * constructor
     * @param value
     */
    public TargetTypeValidation(String value){
        this.value = value;
    }

    @Override
    public void validate() throws ParamsInvalidException {
        Boolean typeSupported = false;
        for (TypeSupported typeObj : TypeSupported.values()) {
            if (typeObj.toString().equals(value.toUpperCase())) {
                typeSupported = true;
                break;
            }
        }
        if(!typeSupported)
            throw new ParamsInvalidException(12, "Target Type is not supported");
    }
}
