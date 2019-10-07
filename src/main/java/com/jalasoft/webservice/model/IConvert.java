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

import com.jalasoft.webservice.entitities.BaseFile;
import com.jalasoft.webservice.entitities.Response;
import com.jalasoft.webservice.error_handler.ConvertException;

/**
 * Convert operations
 * Version : 1.0
 * Date: 9/19/2019
 */
public interface IConvert {
    /**
     * Perform a convertion
     *
     * @param model
     * @return
     */
    Response Convert(BaseFile model) throws ConvertException;
}
