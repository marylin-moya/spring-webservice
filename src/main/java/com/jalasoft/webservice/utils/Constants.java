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

import java.net.UnknownHostException;

public class Constants {
    public final static String APPLICATION_PROPERTIES = "application.properties";
    public static final String BASE_URL = "/api/v1.0";
    public static final String BASE_URL_DOWNLOAD = "/download";
    public static final String HOST_URL = "http://localhost:8080";
    

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

    public static enum URLS
    {
        BASE_URL("base_url","/api/v1.0"),
        DOWNLOAD_URL("download_url", "/download"),
        IMG_URL("img_url", "/img"),
        ORC_URL("orc_url", "/orc");

        private String name;
        private String url;

        URLS(String name, String url){
            this.name = name;
            this.url = url;
        }

        public String getUrl(String name){
            for (URLS url: values()){
                if(url.name.equals(name.toLowerCase())){
                    if(name.toLowerCase("BASE_URL"))
                        return url;
                    else
                        return
                }
            }
        }

        public String toName() {
            return name;
        }

        public String toUrls() {
            return url;
        }



    }
}
