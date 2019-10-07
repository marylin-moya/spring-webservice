/*
 * Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */
package com.jalasoft.webservice.error_handler;

/**
 * Convert Exception Class
 */
public class ConvertException extends Exception {
    public ConvertException(String message, Throwable e) {
        super(message, e);
    }
}
