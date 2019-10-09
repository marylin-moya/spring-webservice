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
import com.jalasoft.webservice.entitities.ImageResponse;
import com.jalasoft.webservice.entitities.Response;
import com.jalasoft.webservice.error_handler.ConvertException;
import com.jalasoft.webservice.utils.FileManager;
import com.jalasoft.webservice.utils.PropertiesReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.springframework.http.HttpStatus;

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
    public Response Convert(BaseFile baseFile) throws ConvertException {
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
            ImageFile metadata = new ImageFile();
            //BaseFile metadata = new BaseFile();
            metadata.setPath(propertiesFile.getValue(targetDirectory));
            metadata.setFileName(String.format("%s.%s", FileManager.getFileNameNoExtension(imageFile.getFileName()), imageFile.getTargetType()));

            ImageResponse imageResponse =
                    new ImageResponse(HttpStatus.OK.name(), HttpStatus.OK.value(), "Image Successfully Converted.");
            imageResponse.setMetadata(metadata);
            return imageResponse;
        } catch (IOException | InterruptedException | IM4JavaException e) {
            LOGGER.error("ImageConvert Exception.{}", e.getMessage());
            throw new ConvertException(e.getMessage(), e);
        }
    }

}
