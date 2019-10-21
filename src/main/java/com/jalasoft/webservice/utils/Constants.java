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

public class Constants {
    public final static String APPLICATION_PROPERTIES = "application.properties";
    public static final String BASE_PATH = "/api/v1.0";
    public static final String IMG_PATH = BASE_PATH + "/img";
    public static final String DOWNLOAD_PATH = "/download";
    public static final String ORC_PATH = BASE_PATH + "/doc";
    public static final String METADATA_EXT = ".csv";
    public static final String AUTHORIZATION = "Authorization";
    public static final String LOGIN_PATH = "/login";
    public static final String IMGCONVERT_PATH = "/convert";
    public static final String OCRCONVERT_PATH = "/ocr";


    public static enum LANGUAGES {
        ENGLISH("english", "eng"),
        FRENCH("french", "fra"),
        URDU("urdu", "urd"),
        SPANISH("spanish", "spa");

        private String name;
        private String suffix;

        LANGUAGES(String name, String suffix) {
            this.name = name;
            this.suffix = suffix;
        }

        public String toName() {
            return name;
        }

        public String toSuffix() {
            return suffix;
        }

        /**
         * Method to get the LANGUAGES object given the Language name
         *
         * @param name of Language
         * @return LANGUAGE instance
         */
        public static LANGUAGES toLanguages(String name) {
            for (LANGUAGES language : values()) {
                if (language.name.equals(name.toLowerCase())) {
                    return language;
                }
            }
            return null;
        }
    }

}
