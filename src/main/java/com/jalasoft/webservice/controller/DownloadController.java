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

import com.jalasoft.webservice.utils.CheckSum;
import com.jalasoft.webservice.utils.FileManager;
import com.jalasoft.webservice.utils.PropertiesManager;
import com.jalasoft.webservice.utils.PropertiesReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

import static com.jalasoft.webservice.utils.Constants.APPLICATION_PROPERTIES;
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
        String fullPathName = PropertiesManager.getInstance().getPropertiesReader().getValue(targetFileKey) + fileName;
        try {
            File file = new File(fullPathName);
            if (file.exists()) {
                try
                {
                    String contenType = fullPathName.substring(fullPathName.lastIndexOf(".") +1).toLowerCase();
                    contenType = String.format("image/%s",contenType); //getContentType
                    BufferedImage image = ImageIO.read(file);
                    response.setContentType(contenType);
                    OutputStream out = response.getOutputStream();
                    ImageIO.write(image, "png", out);
                    out.close();
                }
                catch (IOException e)
                {
                    response.setContentType("application/octet-stream");    // Download the file directly
                    InputStream is = new BufferedInputStream(new FileInputStream(file));
                    FileCopyUtils.copy(is, response.getOutputStream());
                }
            }
        } catch (IOException e) {
            LOGGER.info("File was not download..." + e.getMessage());
        }
    }
}
