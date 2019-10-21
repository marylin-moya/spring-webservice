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
import com.jalasoft.webservice.entitities.OcrFile;
import com.jalasoft.webservice.error_handler.ConvertException;
import com.jalasoft.webservice.error_handler.FileException;
import com.jalasoft.webservice.responses.OcrResponse;
import com.jalasoft.webservice.responses.Response;
import com.jalasoft.webservice.utils.CheckSum;
import com.jalasoft.webservice.utils.FileManager;
import com.jalasoft.webservice.utils.PropertiesManager;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.io.File;

import static com.jalasoft.webservice.utils.Constants.LANGUAGES;

/**
 * Orc convert class
 * Version : 1.0
 * Date: 9/19/2019
 */
public class OcrConvert implements IConvert {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EXTENSION_FORMAT = ".csv";
    private String defaultLanguageProperty = "file.default-language";

    /**
     * Convert method to extract text from a image
     *
     * @param baseFile
     * @return
     * @throws ConvertException
     */
    @Override
    public Response Convert(BaseFile baseFile) throws ConvertException {
        String tesseractPath = "file.tesseract-path";
        String targetDirectory = "file.target-dir";
        String defaultLanguage = PropertiesManager.getInstance().getPropertiesReader().getValue(defaultLanguageProperty);
        String language = null;
        OcrFile ocrFile = (OcrFile) baseFile;
        Tesseract tesseract = new Tesseract();
        LANGUAGES enumLanguage = LANGUAGES.toLanguages(ocrFile.getLang());
        if (enumLanguage == null) {
            LOGGER.info("The {} language is not supported. Trying to extract {} language by default", ocrFile.getLang(), defaultLanguage);
            language = LANGUAGES.toLanguages(defaultLanguage).toSuffix();
        } else {
            language = enumLanguage.toSuffix();
        }

        try {
            String targetPath = PropertiesManager.getInstance().getPropertiesReader().getValue(targetDirectory);
            tesseract.setDatapath(PropertiesManager.getInstance().getPropertiesReader().getValue(tesseractPath));
            tesseract.setLanguage(language);
            String content = tesseract.doOCR(new File(ocrFile.getFullFilePath())).trim();
            BaseFile metadata = new BaseFile();
            metadata.setPath(targetPath);
            String fileName = String.format("%s%s", ocrFile.getFileName(), EXTENSION_FORMAT);
            String fullFilePath = String.format("%s%s", targetPath, fileName);
            metadata.setFileName(fileName);
            metadata.setFullFilePath(fullFilePath);
            FileManager.saveTextIntoFile(String.format("%s%s", metadata.getPath(), metadata.getFileName()), content);
            OcrResponse ocrResponse = new OcrResponse(HttpStatus.OK.name(), HttpStatus.OK.value(), "Text Successfully Extracted.", content);
            metadata.setCheckSum(CheckSum.getCheckSum(fullFilePath));
            ocrResponse.setMetadata(metadata);
            return ocrResponse;
        } catch (TesseractException | FileException e) {
            LOGGER.info("OcrConvert Exception: {}", e.getMessage());
            throw new ConvertException(e.getMessage(), e);
        }
    }
}