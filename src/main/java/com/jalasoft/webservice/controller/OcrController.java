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

import com.jalasoft.webservice.entitities.OcrResponse;
import com.jalasoft.webservice.entitities.OrcFile;
import com.jalasoft.webservice.entitities.TextFile;
import com.jalasoft.webservice.model.IConvert;
import com.jalasoft.webservice.model.OrcConvert;
import com.jalasoft.webservice.utils.FileManager;
import com.jalasoft.webservice.utils.PropertiesReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;

import static com.jalasoft.webservice.utils.Constants.APPLICATION_PROPERTIES;

/**
 * Orc Controller class to implement Rest EndPoint to extract text from a file.
 */
@RestController
public class OcrController {
    private static final Logger LOGGER = LogManager.getLogger();
    private String sourceFileKey = "file.source-dir";
    private String targetFileKey = "file.target-dir";
    private PropertiesReader propertiesFile = new PropertiesReader("src/main/resources/", APPLICATION_PROPERTIES);

    /**
     * /orc endpoint to extract text from a file.
     *
     * @param file     MultipartFile file to upload.
     * @param lang     language String to extract the text.
     * @param checksum checksum String to verify if the file was already uploaded.
     * @return ResponseEntity with the Error or Success result.
     */
    @PostMapping(value = "/orc", consumes = {"multipart/form-data"})
    public ResponseEntity<?> getOrcFromUploadFile(@Valid @NotNull @NotBlank @RequestParam("fileName") MultipartFile file,
                                                  @Valid @NotNull @NotBlank @RequestParam(value = "lang", defaultValue = "eng") String lang,
                                                  @Valid @NotNull @NotBlank @RequestParam("checksum") String checksum) {
        LOGGER.info("/orc endpoint to extract text from {}", file.getOriginalFilename());

        try {
            //Is file Uploaded in Database?, if so return the path and not upload the file
            //If file is not uploaded, upload the file
            FileManager.saveUploadFile(propertiesFile.getValue(sourceFileKey), file);

            //Instance Orc Model with fileName and lang
            OrcFile orcFile = new OrcFile();
            orcFile.setLang(lang);
            orcFile.setPath(propertiesFile.getValue(sourceFileKey));
            orcFile.setFileType(FileManager.getFileNameExtension(file.getOriginalFilename()));
            orcFile.setFileName(FileManager.getFileNameNoExtension(file.getOriginalFilename()));

            IConvert iConvert = new OrcConvert();
            TextFile textFile = (TextFile) iConvert.Convert(orcFile);
            //Call Extract text that will return a OrcResponse Object
            OcrResponse orcResponse = new OcrResponse(HttpStatus.OK.name(), HttpStatus.OK.value(),
                    "Successfully Extracted", textFile.getText());
            return new ResponseEntity(orcResponse, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
