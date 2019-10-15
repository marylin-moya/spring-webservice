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
import com.jalasoft.webservice.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;

import static com.jalasoft.webservice.utils.Constants.DOWNLOAD_PATH;
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
     * @param file     MultipartFile file to upload.
     * @param checksum Checksum String to verify if the file was already uploaded.
     * @param blur     blur
     * @param rotate   Rotate new image
     * @return
     */
    @PostMapping(value = "/convert", consumes = {"multipart/form-data"})
    @ResponseBody
    public Response convertImage(@Valid @NotNull @NotBlank @RequestParam("fileName") MultipartFile file,
                                 @Valid @NotNull @NotBlank @RequestParam("checksum") String checksum,
                                 @Valid @NotNull @NotBlank @RequestParam("targetType") String targetType,
                                 @Valid @NotNull @NotBlank @RequestParam(value = "resize", defaultValue = "0") int resize,
                                 @Valid @RequestParam(value = "blur", defaultValue = "0.0") double blur,
                                 @Valid @RequestParam(value = "rotate", defaultValue = "0.0") double rotate,
                                 @Valid @RequestParam(value = "border", defaultValue = "0.0") int border,
                                 @Valid @RequestParam(value = "grayscale", defaultValue = "false") boolean grayscale,
                                 @Valid @RequestParam(value = "transpose", defaultValue = "false") boolean transpose,
                                 @Valid @RequestParam(value = "transverse", defaultValue = "false") boolean transverse,
                                 @Valid @NotNull @NotBlank @RequestParam(value = "borderColor", defaultValue = "black") String borderColor) {
        LOGGER.info("/img endpoint to convert '{}' image to new format '{}'", file.getOriginalFilename(), targetType);

        try {
            ///Instance Img model
            ImageFile imgFile = new ImageFile();
            imgFile.setFileName(file.getOriginalFilename());
            imgFile.setCheckSum(checksum);
            imgFile.setTargetType(targetType);
            imgFile.setRotate(rotate);
            imgFile.setBlur(blur);
            imgFile.setResize(resize);
            imgFile.setBorderColor(borderColor);
            imgFile.setBorder(border);
            imgFile.setGrayscale(grayscale);
            imgFile.setTransverse(transverse);
            imgFile.setTranspose(transpose);
            imgFile.setPath(PropertiesManager.getInstance().getPropertiesReader().getValue(sourceFileKey));
            imgFile.validate();
            String hostname = ServerUtilities.GetServerHostname();
            //verify if file is saved in database
            String filePathStorage = DBManager.getPath(checksum);
            if (filePathStorage == null) {
                LOGGER.info("Image Controller: File '{}' is not storage in database, Uploading ...", file.getOriginalFilename());
                filePathStorage = PropertiesManager.getInstance().getPropertiesReader().getValue(sourceFileKey);
                DBManager.addFile(checksum, filePathStorage);
                FileManager.saveUploadFile(filePathStorage, file);
            }
            imgFile.setPath(filePathStorage);
            if (!FileManager.isImageFile(String.format("%s%s", filePathStorage, file.getOriginalFilename()))) {
                FileManager.removeFile(String.format("%s%s", filePathStorage, file.getOriginalFilename()));
                return new ImageResponse(HttpStatus.BAD_REQUEST.name(),
                        HttpStatus.BAD_REQUEST.value(), "The file to convert is not an image file");
            }
            IConvert iConvert = new ImageConvert();
            ImageResponse imageResponse = (ImageResponse) iConvert.Convert(imgFile);
            BaseFile metadata = imageResponse.getMetadata();
            //generate metadata file and zip image and metadata
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
            return new ImageResponse(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value(),
                    convertion.getMessage());
        } catch (ImageProcessingException ie) {
            return new ErrorResponse(HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value(),
                    String.format("Error with Get Metadata information: %s", ie.getMessage()));
        }

    }
}
