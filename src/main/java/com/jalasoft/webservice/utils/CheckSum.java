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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.jalasoft.webservice.utils.Constants.APPLICATION_PROPERTIES;

public class CheckSum {
    private PropertiesReader propertiesFile = new PropertiesReader("src/main/resources/", APPLICATION_PROPERTIES);

    public String getCheckSum(String filePath) {
        try {
            MessageDigest md = MessageDigest.getInstance(propertiesFile.getValue("tesseract-path")); //SHA, MD2, MD5, SHA-256, SHA-384...
            DigestInputStream dis = new DigestInputStream(new FileInputStream(filePath), md);
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();

            // bytes to hex
            StringBuilder result = new StringBuilder();
            for (byte b : md.digest()) {
                result.append(String.format("%02x", b));
            }
            return result.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}