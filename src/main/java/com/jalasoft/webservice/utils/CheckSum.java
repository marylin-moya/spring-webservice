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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.jalasoft.webservice.utils.Constants.APPLICATION_PROPERTIES;

/***
 * Class to calculate the checksum related to specific file
 *  Version : 1.0
 *  Date: 9/20/2019
 */
public class CheckSum {
    private PropertiesReader propertiesFile = new PropertiesReader("src/main/resources/", APPLICATION_PROPERTIES);
    private static final Logger LOGGER = LogManager.getLogger();

    public String getCheckSum(String filePath) {
        try {
            MessageDigest md = MessageDigest.getInstance(propertiesFile.getValue("tesseract-path")); //SHA, MD2, MD5, SHA-256, SHA-384...
            DigestInputStream dis = new DigestInputStream(new FileInputStream(filePath), md);
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();
            StringBuilder result = new StringBuilder();

            for (byte b : md.digest()) {
                result.append(String.format("%02x", b));
            }
            return result.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.info("CheckSum File Not Found Exception. {}", e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.info("CheckSum Algorithm Exception. {}", e.getMessage());
        } catch (IOException e) {
            LOGGER.info("CheckSum IOException. {}", e.getMessage());
        }
        return null;
    }
}