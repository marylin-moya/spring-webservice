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
import com.jalasoft.webservice.utils.FileManager;
import com.jalasoft.webservice.utils.PropertiesReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;

import java.io.IOException;

import static com.jalasoft.webservice.utils.Constants.APPLICATION_PROPERTIES;

/**
 * Image Convert class.
 */
public class ImageConvert implements IConvert {
    private static final Logger LOGGER = LogManager.getLogger();
    private PropertiesReader propertiesFile = new PropertiesReader("src/main/resources/", APPLICATION_PROPERTIES);

    /**
     * Convert Method to change a image to image.
     * @param baseFile
     * @return
     */
    @Override
    public BaseFile Convert(BaseFile baseFile) {
        String imageMagicPath = "file.imagemagic-path";
        String targetDirectory = "file.target-dir";
        String grayScale = "Grayscale";
        ImageFile imageFile = (ImageFile) baseFile;
        ProcessStarter.setGlobalSearchPath(propertiesFile.getValue(imageMagicPath));

        //Create the operation, add images and operators/options
        IMOperation op = new IMOperation();
        op.addImage(String.format("%s%s", imageFile.getPath(), imageFile.getFileName()));
        op.rotate(imageFile.getRotate()); //90.0
        op.blur(imageFile.getBlur());
        op.bordercolor(imageFile.getBorderColor());
        op.border(imageFile.getBorder()); //10
        if (imageFile.getResize() != 0) {
            op.resize(imageFile.getResize());
        }
        if (imageFile.isTranspose()) {
            op.transpose();
        }
        if (imageFile.isTransverse()) {
            op.transverse();
        }
        if (imageFile.isGrayscale()) {
            op.type(grayScale);
        }

        op.addImage(String.format("%s%s.%s",
                propertiesFile.getValue(targetDirectory),
                FileManager.getFileNameNoExtension(imageFile.getFileName()),
                imageFile.getTargetType()));

        try {
            ConvertCmd cmd = new ConvertCmd();
            cmd.run(op);
            //ImageRespo
        } catch (IOException | InterruptedException | IM4JavaException e) {
            LOGGER.error("ImageConvert Exception.{}", e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        ImageFile imageFile = new ImageFile();
        imageFile.setFileName("axios1.jpg");
        imageFile.setPath("D:\\dev-fundamentals-2\\spring-webservice\\src\\main\\resources\\source-dir\\");
        imageFile.setTargetType("png");
        imageFile.setRotate(0.0);  //0.0 by default double
        imageFile.setBlur(200.0);   //0.0 by default  double
        imageFile.setResize(400);     //0 by default  int
        imageFile.setBorderColor("red");  // black by default string
        imageFile.setBorder(20);   // 0 by default int
        imageFile.setGrayscale(false); //false by default
        imageFile.setTranspose(false); //false by default
        imageFile.setTransverse(false);//false by default

        ImageConvert imageConvert = new ImageConvert();
        imageConvert.Convert(imageFile);
    }
}
