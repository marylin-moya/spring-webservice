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
 * ParamsInvalidException class.
 */
public class ParamsInvalidException extends Exception {
    int code;
    String message;
    String param;

    /**
     * ParamsInvalidException Constructor.
     *
     * @param code
     * @param param
     */
    public ParamsInvalidException(int code, String param) {
        this.code = code;
        this.param = param;
    }

    /**
     * Get Message
     *
     * @return
     */
    public String getMessage() {
        switch (this.code) {
            case 10:
                this.message = String.format("the param %s is null", this.param);
                break;
            case 11:
                this.message = String.format("the param %s is empty", this.param);
                break;
            case 12:
                this.message = String.format("the param %s needs to be higher than 0", this.param);
                break;
            case 13:
                this.message = String.format("the param %s does not have a valid color value", this.param);
                break;
            case 15:
                this.message = String.format("The param %s is not supported to convert.", this.param);
                break;
            case 16:
                this.message = String.format("The %s params are wrong.", this.param);
                break;
        }
        return this.message;
    }

}
