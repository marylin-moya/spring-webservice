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
import com.jalasoft.webservice.error_handler.ConvertException;
import com.jalasoft.webservice.responses.ImageResponse;
import com.jalasoft.webservice.responses.Response;
import com.jalasoft.webservice.utils.CheckSum;
import com.jalasoft.webservice.utils.FileManager;
import com.jalasoft.webservice.utils.PropertiesManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;

/**
 * Image Convert class.
 */
public class ImageConvert implements IConvert {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Convert Method to change a image to image.
     *
     * @param baseFile
     * @return
     */
    @Override
    public Response Convert(BaseFile baseFile) throws ConvertException {
        String imageMagicPath = "file.imagemagic-path";
        String targetDirectory = "file.target-dir";
        String grayScale = "Grayscale";
        ImageFile imageFile = (ImageFile) baseFile;
        ProcessStarter.setGlobalSearchPath(PropertiesManager.getInstance().getPropertiesReader().getValue(imageMagicPath));

        //Create the operation, add images and operators/options
        IMOperation op = new IMOperation();
        op.addImage(imageFile.getFullFilePath());
        op.rotate(imageFile.getRotate());
        op.blur(imageFile.getBlur());
        op.bordercolor(imageFile.getBorderColor());
        op.border(imageFile.getBorder());
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
        String targetPath = PropertiesManager.getInstance().getPropertiesReader().getValue(targetDirectory);
        String convertedFileName = String.format("%s.%s",
                FileManager.getFileNameNoExtension(imageFile.getFileName()), imageFile.getTargetType());
        String convertedImage = String.format("%s%s", targetPath, convertedFileName);
        op.addImage(convertedImage);

        try {
            ConvertCmd cmd = new ConvertCmd();
            cmd.run(op);
            BaseFile metadata = new BaseFile();
            metadata.setPath(targetPath);
            metadata.setFileName(convertedFileName);
            metadata.setFullFilePath(convertedImage);
            metadata.setSize(new File(convertedImage).length());
            metadata.setCheckSum(CheckSum.getCheckSum(convertedImage));
            ImageResponse imageResponse =
                    new ImageResponse(HttpStatus.OK.name(), HttpStatus.OK.value(), "Image Successfully Converted.");
            imageResponse.setMetadata(metadata);
            return imageResponse;
        } catch (IOException | InterruptedException | IM4JavaException e) {
            LOGGER.error("ImageConvert Exception: {}", e.getMessage());
            throw new ConvertException(e.getMessage(), e);
        }
    }

}
