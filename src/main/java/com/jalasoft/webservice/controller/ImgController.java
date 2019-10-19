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

import com.drew.imaging.ImageProcessingException;
import com.jalasoft.webservice.entitities.BaseFile;
import com.jalasoft.webservice.entitities.ImageFile;
import com.jalasoft.webservice.error_handler.ConvertException;
import com.jalasoft.webservice.error_handler.ParamsInvalidException;
import com.jalasoft.webservice.model.DBManager;
import com.jalasoft.webservice.model.IConvert;
import com.jalasoft.webservice.model.ImageConvert;
import com.jalasoft.webservice.responses.ErrorResponse;
import com.jalasoft.webservice.responses.ImageResponse;
import com.jalasoft.webservice.responses.Response;
import com.jalasoft.webservice.utils.FileManager;
import com.jalasoft.webservice.utils.MetadataExtractor;
import com.jalasoft.webservice.utils.PropertiesManager;
import com.jalasoft.webservice.utils.ServerUtilities;
import com.jalasoft.webservice.utils.ZipManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

import static com.jalasoft.webservice.utils.Constants.DOWNLOAD_PATH;
import static com.jalasoft.webservice.utils.Constants.IMGCONVERT_PATH;
import static com.jalasoft.webservice.utils.Constants.IMG_PATH;

/**
 * Img Controller class to implement Rest endpoint related to convert a image
 */

@RestController
@RequestMapping(IMG_PATH)
public class ImgController {
    private static final Logger LOGGER = LogManager.getLogger();
    private String sourceFileKey = "file.source-dir";//class to read keys
    private static String PATH_DOWNLOAD_FILE = DOWNLOAD_PATH + "/file/";
    private static String PATH_DOWNLOAD_ZIP_FILE = DOWNLOAD_PATH + "/filemetadatazip/";
    private static String PORT = ":8080";

    /**
     * @param imageFile MultipartFile file to upload.*
     * @return
     */
    @PostMapping(value = IMGCONVERT_PATH)
    @ResponseBody
    public Response convertImage(@RequestBody ImageFile imageFile) {
        LOGGER.info("/img endpoint to convert '{}' image to new format '{}'", imageFile.getFileName(), imageFile.getTargetType());
        String sourcePath = PropertiesManager.getInstance().getPropertiesReader().getValue(sourceFileKey);
        String originFilePath = String.format("%s\\%s", imageFile.getPath(), imageFile.getFileName());
        File file = new File(originFilePath);
        try {
            //Get Server hostname
            String hostname = ServerUtilities.GetServerHostname();

            //Verify if file to be converted is an image
            if (!FileManager.isImageFile(originFilePath)) {
                return new ErrorResponse(HttpStatus.BAD_REQUEST.name(),
                        HttpStatus.BAD_REQUEST.value(), "The file to convert is not an image file");
            }

            //Add additional imageFile attributes
            imageFile.setPath(sourcePath);
            imageFile.setFullFilePath(String.format("%s%s", sourcePath, imageFile.getFileName()));
            imageFile.validate();

            //verify if file is saved in database
            String filePathStorage = DBManager.getPath(imageFile.getCheckSum());
            if (filePathStorage == null) {
                LOGGER.info("Image Controller: File '{}' is not storage in database, Uploading ...", imageFile.getFileName());
                DBManager.addFile(imageFile.getCheckSum(), imageFile.getFullFilePath());
                FileManager.saveUploadFile(sourcePath, file);
            } else {
                imageFile.setFullFilePath(filePathStorage);
            }

            //Convert an image to another format
            IConvert iConvert = new ImageConvert();
            ImageResponse imageResponse = (ImageResponse) iConvert.Convert(imageFile);
            BaseFile metadata = imageResponse.getMetadata();

            //generate metadata file and zip image
            String metadataFileName = MetadataExtractor.generateMetadataFile(String.format("%s%s", metadata.getPath(), metadata.getFileName()));
            String zipFile = ZipManager.zipFiles(String.format("%s%s", metadata.getPath(), metadata.getFileName()), metadataFileName);


            String urlDownload = String.format("%s%s%s%s", hostname, PORT, PATH_DOWNLOAD_FILE, metadata.getFileName());
            String urlMetadataDownload = String.format("%s%s%s%s", hostname, PORT, PATH_DOWNLOAD_ZIP_FILE, FileManager.getFileName(zipFile));
            imageResponse.setUrlPreview(urlDownload);
            imageResponse.setUrlDownload(urlMetadataDownload);
            LOGGER.info("New file is available in following link {}", urlDownload);
            return imageResponse;

        } catch (ParamsInvalidException e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST.name(),
                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
        } catch (IOException e) {
            LOGGER.error("Image Controller: File was not Found. Error '{}' ", e.getMessage());
            return new ErrorResponse(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value(),
                    "File was not found");
        } catch (NullPointerException | IllegalStateException e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST.name(),
                    HttpStatus.BAD_REQUEST.value(), "The Image File does not exist");
        } catch (ConvertException convertion) {
            LOGGER.error("Image Controller: Error in conversion operation '{}' ", convertion.getMessage());
            return new ErrorResponse(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value(),
                    convertion.getMessage());
        } catch (ImageProcessingException ie) {
            return new ErrorResponse(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value(),
                    String.format("Error with Get Metadata information: %s", ie.getMessage()));
        }

    }
}
