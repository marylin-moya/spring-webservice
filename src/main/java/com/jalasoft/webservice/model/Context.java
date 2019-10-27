/*
 * Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */

package com.jalasoft.webservice.model;

import com.jalasoft.webservice.error_handler.ParamsInvalidException;
import com.jalasoft.webservice.model.validate.IValidateStrategy;

import java.util.List;

/**
 * Class to manage the validations
 */
public class Context {
    List<IValidateStrategy> strategyList;
    public Context(List<IValidateStrategy> strategies){
        this.strategyList = strategies;
    }

    public void validate() throws ParamsInvalidException{
        for (IValidateStrategy stra:this.strategyList
             ) {
            stra.validate();
        }
        }
    }
