/*
 *
 *  Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with Jalasoft.
 *
 */
package com.jalasoft.webservice.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public final static String APPLICATION_PROPERTIES = "application.properties";
    public static Map<String, String> LANGUAGE = new HashMap<String, String>() {
        {
            put("eng", "English");
            put("fra", "French");
            put("urd", "Urdu");
            put("spa", "Spanish");
        }
    };
}
