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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/***
 * Class to compress files
 */
public class ZipManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static String targetFileKey = "file.target-dir";

    /***
     * Method to compress multiple files in ZIP format. Name of zip is first file + .zip
     * @param filePaths
     * @return
     * @throws Exception
     */
    public static String zipFiles(String... filePaths) throws IOException {
        String zipFileName = null;
        File firstFile = new File(filePaths[0]);
        zipFileName = String.format("%s%s", PropertiesManager.getInstance().getPropertiesReader().getValue(targetFileKey), firstFile.getName()).concat(".zip");
        FileOutputStream fos = new FileOutputStream(zipFileName);
        ZipOutputStream zos = new ZipOutputStream(fos);
        for (String aFile : filePaths) {
            zos.putNextEntry(new ZipEntry(new File(aFile).getName()));

            byte[] bytes = Files.readAllBytes(Paths.get(aFile));
            zos.write(bytes, 0, bytes.length);
            zos.closeEntry();
        }
        zos.close();
        return zipFileName;
    }
}