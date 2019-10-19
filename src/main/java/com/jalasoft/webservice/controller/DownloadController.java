/*
 * Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */

package com.jalasoft.webservice.controller;

import com.jalasoft.webservice.utils.FileManager;
import com.jalasoft.webservice.utils.PropertiesManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.jalasoft.webservice.utils.Constants.DOWNLOAD_PATH;

/***
 * Download Controller class to download a file
 */

@RestController
@RequestMapping(DOWNLOAD_PATH)
public class DownloadController {
    private static final Logger LOGGER = LogManager.getLogger();
    private String targetFileKey = "file.target-dir";

    /***
     *
     * @param response
     * @param fileName  File Name to download
     */
    @GetMapping("/file/{fileName:.+}")
    public void getFile(HttpServletResponse response,
                        @PathVariable("fileName") String fileName) {
        String fullPathName = String.format("%s%s", PropertiesManager.getInstance().getPropertiesReader().getValue(targetFileKey), fileName);
        String commonContentType = "application/octet-stream";
        LOGGER.info("Download {} file", fileName);
        try {
            File file = new File(fullPathName);
            if (file.exists()) {
                try {
                    String extension = FileManager.getFileNameExtension(fullPathName).toLowerCase();
                    String imageFormat = "image";
                    String contentType = String.format("%s/%s", imageFormat, extension); //getContentType
                    BufferedImage image = ImageIO.read(file);
                    response.setContentType(contentType);
                    OutputStream out = response.getOutputStream();
                    ImageIO.write(image, extension, out);
                    out.close();
                } catch (IOException e) {
                    LOGGER.error("Exception to download the file: {}", e.getMessage());
                    response.setContentType(commonContentType);    // Download the file directly
                    InputStream is = new BufferedInputStream(new FileInputStream(file));
                    FileCopyUtils.copy(is, response.getOutputStream());
                }
            }
        } catch (IOException e) {
            LOGGER.info("File was not download: {}", e.getMessage());
        }
    }

    /***
     *
     * @param response
     * @param fileName  File Name to download
     */
    @GetMapping("/filemetadatazip/{fileName:.+}")
    public void getFileAndMetadataInfo(HttpServletResponse response,
                                       @PathVariable("fileName") String fileName) {
        String fullPathName = String.format("%s%s", PropertiesManager.getInstance().getPropertiesReader().getValue(targetFileKey), fileName);
        String commonContentType = "application/octet-stream";
        try {
            File file = new File(fullPathName);
            if (file.exists()) {
                response.setContentType(commonContentType);    // Download the file directly
                InputStream is = new BufferedInputStream(new FileInputStream(fullPathName));
                FileCopyUtils.copy(is, response.getOutputStream());
            }
        } catch (IOException ex) {
            LOGGER.info("It is not possible download zip File..." + ex.getMessage());
        }
    }
}
