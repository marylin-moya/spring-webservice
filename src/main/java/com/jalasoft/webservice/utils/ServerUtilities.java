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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Utilities for Server site
 */
public class ServerUtilities {
    private static final Logger LOGGER = LogManager.getLogger();

    /***
     * Get Hostname of server
     * @return
     */
    public static String GetServerHostname() {
        InetAddress ip;
        String hostname;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            return String.format("http://%s", hostname);

        } catch (UnknownHostException e) {
            LOGGER.error("Error : is not possible get Hostname ", e.getMessage());
            return null;
        }
    }

}
