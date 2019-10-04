/*
 * Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */

package com.jalasoft.webservice.model;

import com.jalasoft.webservice.entitities.BaseFile;
import com.jalasoft.webservice.entitities.ImageFile;
import com.jalasoft.webservice.utils.PropertiesReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.jalasoft.webservice.utils.Constants.APPLICATION_PROPERTIES;

/**
 * Image convert class
 * Version : 1.0
 * Date: 9/20/2019
 */
public class ImageConvert implements IConvert {
    private PropertiesReader propertiesFile = new PropertiesReader("src/main/resources/", APPLICATION_PROPERTIES);
    private String targetDirectory = "file.target-dir";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public BaseFile Convert(BaseFile model) {
        BufferedImage img = null;
        File file = null;
        try {
            file = new File(String.format("%s%s", model.getPath(), model.getFileName()));
            img = ImageIO.read(file);
        }catch (IOException e){
            LOGGER.error("Exception at the moment to read the Image file: ", e.getMessage());
        }
        //write image
        String newName = "test" + model.getFileName();
        try{

            file = new File(String.format("%s%s", propertiesFile.getValue(targetDirectory), newName));
            ImageIO.write(img, "jpg", file);
        }catch(IOException e){
            LOGGER.error("Exception at the moment to save the GrayScale Image: ", e.getMessage());
        }
        ImageFile imageConverted = new ImageFile();
        imageConverted.setPath(propertiesFile.getValue(targetDirectory));
        imageConverted.setFileName(newName);
        return imageConverted;
    }
}
