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

import com.jalasoft.webservice.entitities.ImageFile;
import com.jalasoft.webservice.entitities.ImageResponse;
import com.jalasoft.webservice.entitities.Response;
import com.jalasoft.webservice.error_handler.ConvertException;
import com.jalasoft.webservice.model.DBManager;
import com.jalasoft.webservice.model.IConvert;
import com.jalasoft.webservice.model.ImageConvert;
import com.jalasoft.webservice.utils.FileManager;
import com.jalasoft.webservice.utils.PropertiesReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;

import static com.jalasoft.webservice.utils.Constants.*;

/**
 * Img Controller class to implement Rest endpoint related to convert a image
 */

@RestController
@RequestMapping(ImgController.BASE_URL_IMG)
public class ImgController {
    private static final Logger LOGGER = LogManager.getLogger();
    private String sourceFileKey = "file.source-dir";//class to read keys
    private String targetFileKey = "file.target-dir";
    public static final String BASE_URL_IMG = BASE_URL + "/img";
    private PropertiesReader propertiesFile = new PropertiesReader("src/main/resources/", APPLICATION_PROPERTIES);///x.getResources()

    /**
     *
     * @param file          MultipartFile file to upload.
     * @param checksum      Checksum String to verify if the file was already uploaded.
     *
     * @param blur          blur
     * @param rotate        Rotate new image
     * @return
     */
    @PostMapping(value = "/convert", consumes = {"multipart/form-data"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
   public ImageResponse convertImage(@Valid @NotNull @NotBlank @RequestParam("fileName") MultipartFile file,
                                          @Valid @NotNull @NotBlank @RequestParam("checksum") String checksum,
                                          @Valid @NotNull @NotBlank @RequestParam("targetType") String targetType,
                                          @Valid @NotNull @NotBlank @RequestParam(value = "resize", defaultValue = "0") String resize,
                                          @Valid @RequestParam(value ="blur", defaultValue = "0")  String blur,
                                          @Valid @RequestParam(value ="rotate", defaultValue = "0")  String rotate,
                                          @Valid @RequestParam(value ="border", defaultValue = "0")  String border,
                                          @Valid @RequestParam(value ="grayscale", defaultValue = "false")  String grayscale,
                                          @Valid @RequestParam(value ="transpose", defaultValue = "false")  String transpose,
                                          @Valid @RequestParam(value ="transverse", defaultValue = "false")  String transverse,
                                          @Valid @NotNull @NotBlank @RequestParam(value ="borderColor", defaultValue = "0") String borderColor) {
        LOGGER.info("/img endpoint to convert '{}' image to new format '{}'", file.getOriginalFilename(), targetType);
        try {
            if (file == null || file.isEmpty()) {
                return new ImageResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(),
                        "Img Controller: Failed to convert empty file");

            }
            if (checksum == null || checksum.isEmpty()) {
                return new ImageResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(),
                        "Empty checksum is not allowed");

            }
            //verify if file is saved in database
            String filePathStorage = null;//DBManager.getPath(checksum);
            if (filePathStorage == null) {
                LOGGER.info("Image Controller: File '{}' is not storage in database, Uploading ...", file.getOriginalFilename());
                filePathStorage = propertiesFile.getValue(sourceFileKey);
                //DBManager.addFile(checksum, filePathStorage);
                FileManager.saveUploadFile(filePathStorage, file);
            }
            if(!FileManager.isImageFile(String.format("%s%s", filePathStorage, file.getOriginalFilename()))){
                FileManager.removeFile(String.format("%s%s", filePathStorage, file.getOriginalFilename()));
                return new ImageResponse(HttpStatus.BAD_REQUEST.name(),
                        HttpStatus.BAD_REQUEST.value(), "The file to convert is not an image file");
            }
            ///Instance Img model
            ImageFile imgFile = new ImageFile();
            imgFile.setPath(filePathStorage);
            imgFile.setFileName(file.getOriginalFilename());
            imgFile.setTargetType(targetType);
            imgFile.setRotate(Double.parseDouble(rotate));
            imgFile.setBlur(Double.parseDouble(blur));
            imgFile.setResize(Integer.parseInt(resize));
            imgFile.setBorder(Integer.parseInt(border));
            imgFile.setBorderColor(borderColor);
            imgFile.setGrayscale(Boolean.parseBoolean(grayscale));
            imgFile.setTranspose(Boolean.parseBoolean(transpose));
            imgFile.setTransverse(Boolean.parseBoolean(transverse));
            IConvert iConvert = new ImageConvert();
            ImageFile imageConverted = (ImageFile) iConvert.Convert(imgFile);
            String urlDownload = HOST_URL + BASE_URL_DOWNLOAD + "/file/" + imageConverted.getFileName();
            LOGGER.info("New file is available in following link {}", urlDownload);
            return  new ImageResponse(HttpStatus.OK.name(), HttpStatus.OK.value(),
                    "Conversion image was success",
                    String.format("Download new file using the following url : %s", urlDownload));

        } catch (IOException e) {
            LOGGER.error("Image Controller: File was not Found. Error '{}' ", e.getMessage());
            return  new ImageResponse(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value(),
                    "File was not found");
        } catch (NullPointerException | IllegalStateException e) {
        return new ImageResponse(HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(), "The Image File does not exist");
        } catch (ConvertException convertion) {
            LOGGER.error("Image Controller: Error in conversion operation '{}' ", convertion.getMessage());
            return  new ImageResponse(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value(),
        convertion.getMessage());///maejo constantes, common constants, error constants
        }
    }
}
