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

public class ParamsInvalidException extends Exception{
    int code;
    String message;
    String param;
    public ParamsInvalidException(int code, String params)
    {
        this.code = code;
        this.param = params;
    }

    public String getMesage(){
        switch (this.code){
            case 10:
                this.message ="The parameter "+ this.param + "is null";
                break;
            case 11:
                this.message ="The parameter "+ this.param + "is empty";
                break;
        }
        return message;
    }
}
