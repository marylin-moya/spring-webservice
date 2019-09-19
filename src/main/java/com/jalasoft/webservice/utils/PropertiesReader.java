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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    private static final Logger LOGGER = LogManager.getLogger();
    private Properties userProperties;
    private String projectPath = String.format("%s/", System.getProperty("user.dir"));

    /**
     * Constructor to load properties file.
     *
     * @param filePath file path.
     * @param fileName file name.
     */
    public PropertiesReader(String filePath, String fileName) {
        String propertyFilePath = (String.format("%s%s%s", projectPath, filePath, fileName));
        try (FileInputStream fileInputStream = new FileInputStream(propertyFilePath)) {
            userProperties = new Properties();
            userProperties.load(fileInputStream);
        } catch (IOException e) {
            LOGGER.error("Error reading properties {} file: {}", propertyFilePath, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the property.
     *
     * @param key string property Name.
     * @return String the property that match with key.
     */
    public String getValue(final String key) {
        return userProperties.getProperty(key);
    }

}
