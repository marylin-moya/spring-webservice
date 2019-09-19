/*
 * Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */

package com.jalasoft.webservice.utils;

import java.util.HashMap;
import java.util.Map;

/***
 * ConfigurationVariable : Information related to project configuration
 */
public final class ConfigurationVariable {
    public static String IMG_FOLDER = "src/images/";
    public static String TXT_FOLDER = "src/txt/";
    public static String TESSERAL_PATH = "src/thridParty/Tess4J/tessdata";
    public static String TYPE_CHECKSUM = "SHA-256";
    public static String DEFAULT_LANGUAGE = "ENGLISH";
    public static Map<String, String> LANGUAGE = new HashMap<String, String>()
    {
        {
            put(DEFAULT_LANGUAGE, "eng");
            put("FRENCH", "fra");
            put("URDU", "urd");
            put("SPANISH", "spa");
        }
    };
}
