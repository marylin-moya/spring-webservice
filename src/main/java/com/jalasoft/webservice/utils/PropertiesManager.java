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

/**
 * PropertiesManager Singleton class.
 */
public class PropertiesManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static PropertiesManager instance;
    private static PropertiesReader propertiesReader;
    private String fileName = "application.properties";

    /**
     * PropertiesManager constructor.
     */
    private PropertiesManager() {
        this.init();
    }

    /**
     * Get the PropertiesReader instance.
     *
     * @return
     */
    public static PropertiesReader getPropertiesReader() {
        return propertiesReader;
    }

    /**
     * Get the PropertiesManager Instance.
     *
     * @return
     */
    public static PropertiesManager getInstance() {
        if (instance == null) {
            instance = new PropertiesManager();
        }
        return instance;
    }

    /**
     * Initialize the PropertiesReader with "application.properties" file
     */
    private void init() {
        LOGGER.info("Initializing PropertiesManager Instance...");
        String propertyFilePath = getClass().getClassLoader().getResource(fileName).getPath();
        propertiesReader = new PropertiesReader(propertyFilePath);
    }
}
