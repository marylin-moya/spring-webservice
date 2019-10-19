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

import com.jalasoft.webservice.entitities.OcrFile;
import com.jalasoft.webservice.error_handler.ConvertException;
import com.jalasoft.webservice.error_handler.ParamsInvalidException;
import com.jalasoft.webservice.model.DBManager;
import com.jalasoft.webservice.model.IConvert;
import com.jalasoft.webservice.model.OcrConvert;
import com.jalasoft.webservice.responses.ErrorResponse;
import com.jalasoft.webservice.responses.Response;
import com.jalasoft.webservice.utils.CheckSum;
import com.jalasoft.webservice.utils.FileManager;
import com.jalasoft.webservice.utils.PropertiesManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

import static com.jalasoft.webservice.utils.Constants.ORC_PATH;

/**
 * Orc Controller class to implement Rest EndPoint to extract text from a file.
 */
@RestController
@RequestMapping(ORC_PATH)
public class OcrController {
    private static final Logger LOGGER = LogManager.getLogger();
    private String sourceFileKey = "file.source-dir";

    /**
     * /orc endpoint to extract text from a file.
     *
     * @param ocrFile     OCR object.
     * @return ResponseEntity with the Error or Success result.
     */
    @PostMapping(value = "/orc")
    public Response getOrcFromUploadFile(@RequestBody OcrFile ocrFile) {
        LOGGER.info("/orc endpoint to extract '{}' text from '{}'", ocrFile.getLang(), ocrFile.getFileName());
        String originFile =  String.format("%s/%s", ocrFile.getPath(), ocrFile.getFileName());
        File file = new File(originFile);
        try {
            String sourcePath = PropertiesManager.getInstance().getPropertiesReader().getValue(sourceFileKey);
            String checksum = CheckSum.getCheckSum(originFile);
            ocrFile.setCheckSum(checksum);
            ocrFile.validate();
            String filePath = DBManager.getPath(ocrFile.getCheckSum());

            //If file is not uploaded, upload the file
            if (filePath == null) {
                LOGGER.info("File is not stored, Uploading...");
                filePath = PropertiesManager.getInstance().getPropertiesReader().getValue(sourceFileKey);
                ocrFile.setPath(filePath);
                DBManager.addFile(checksum, ocrFile.getFullFilePath());
                FileManager.saveUploadFile(filePath, file);
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