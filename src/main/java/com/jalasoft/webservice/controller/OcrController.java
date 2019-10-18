/*
 *
 *  Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with Jalasoft.
 *
 */
package com.jalasoft.webservice.controller;

import com.jalasoft.webservice.entitities.Cache;
import com.jalasoft.webservice.entitities.OcrFile;
import com.jalasoft.webservice.error_handler.ConvertException;
import com.jalasoft.webservice.error_handler.ParamsInvalidException;
import com.jalasoft.webservice.model.DBManager;
import com.jalasoft.webservice.model.IConvert;
import com.jalasoft.webservice.model.OcrConvert;
import com.jalasoft.webservice.responses.ErrorResponse;
import com.jalasoft.webservice.responses.Response;
import com.jalasoft.webservice.utils.FileManager;
import com.jalasoft.webservice.utils.PropertiesManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;

import static com.jalasoft.webservice.utils.Constants.CACHE;
import static com.jalasoft.webservice.utils.Constants.ORC_PATH;

/**
 * Orc Controller class to implement Rest EndPoint to extract text from a file.
 */
@RestController
//interceptores y filtros, se ejecuta antes de /*
@RequestMapping(ORC_PATH)
public class OcrController {
    private static final Logger LOGGER = LogManager.getLogger();
    private String sourceFileKey = "file.source-dir";

    /**
     * /orc endpoint to extract text from a file.
     *
     * @param file     MultipartFile file to upload.
     * @param lang     language String to extract the text.
     * @param checksum checksum String to verify if the file was already uploaded.
     * @return ResponseEntity with the Error or Success result.
     */
    @PostMapping(value = "/orc", consumes = {"multipart/form-data"})
    public Response getOrcFromUploadFile(@Valid @NotNull @NotBlank @RequestParam("fileName") MultipartFile file,
                                         @Valid @NotNull @NotBlank @RequestParam(value = "lang", defaultValue = "english") String lang,
                                         @Valid @NotNull @NotBlank @RequestParam("checksum") String checksum, HttpServletRequest req) {
        LOGGER.info("/orc endpoint to extract '{}' text from '{}'", lang, file.getOriginalFilename());

        try {
            String sourcePath = PropertiesManager.getInstance().getPropertiesReader().getValue(sourceFileKey);
            String fileName = file.getOriginalFilename();

            //verify if token is cache
            String auth = req.getHeader(CACHE);
            String token = auth.split(" ")[1];
            if (!Cache.getInstance().isValid(token)) {
                return new ErrorResponse(HttpStatus.UNAUTHORIZED.name(),
                        HttpStatus.UNAUTHORIZED.value(), "The user is not authorized");
            }

            //Instance Orc Model with fileName and lang
            OcrFile ocrFile = new OcrFile();
            ocrFile.setLang(lang);
            ocrFile.setPath(sourcePath);
            ocrFile.setFileName(fileName);
            ocrFile.setCheckSum(checksum);
            ocrFile.setFullFilePath(String.format("%s%s", sourcePath, fileName));
            ocrFile.validate();

            String filePath = DBManager.getPath(checksum);

            //If file is not uploaded, upload the file
            if (filePath == null) {
                LOGGER.info("File is not stored, Uploading...");
                DBManager.addFile(checksum, ocrFile.getFullFilePath());
                FileManager.saveUploadFile(sourcePath, file);
            } else {
                ocrFile.setFullFilePath(filePath);
            }

            IConvert iConvert = new OcrConvert();
            return iConvert.Convert(ocrFile);
        } catch (ParamsInvalidException | ConvertException e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST.name(),
                    HttpStatus.BAD_REQUEST.value(), e.getMessage());
        } catch (IOException e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST.name(),
                    HttpStatus.BAD_REQUEST.value(), "IOException");
        } catch (NullPointerException | IllegalStateException e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST.name(),
                    HttpStatus.BAD_REQUEST.value(), "The file does not exist");
        }
    }
}