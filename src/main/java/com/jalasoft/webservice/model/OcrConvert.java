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
import com.jalasoft.webservice.entitities.OcrResponse;
import com.jalasoft.webservice.entitities.Response;
import com.jalasoft.webservice.error_handler.ConvertException;
import com.jalasoft.webservice.utils.PropertiesReader;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.io.File;

import static com.jalasoft.webservice.utils.Constants.APPLICATION_PROPERTIES;
import static com.jalasoft.webservice.utils.Constants.LANGUAGES;

/**
 * Orc convert class
 * Version : 1.0
 * Date: 9/19/2019
 */
public class OcrConvert implements IConvert {
    private PropertiesReader propertiesFile = new PropertiesReader("src/main/resources/", APPLICATION_PROPERTIES);
    private static final Logger LOGGER = LogManager.getLogger();
    private String defaultLanguageProperty = "file.default-language";

    @Override
    public Response Convert(BaseFile baseFile) throws ConvertException {
        String tesseractPath = "file.tesseract-path";
        String defaultLanguage = propertiesFile.getValue(defaultLanguageProperty);
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
            tesseract.setDatapath(propertiesFile.getValue(tesseractPath));
            tesseract.setLanguage(language);
            String text = tesseract.doOCR(new File(String.format("%s%s", ocrFile.getPath(), ocrFile.getFullFileName()))).trim();
            return new OcrResponse(HttpStatus.OK.name(), HttpStatus.OK.value(), "Text Successfully Extracted.", text);
        } catch (TesseractException e) {
            LOGGER.info("OcrConvert Exception");
            throw new ConvertException(e.getMessage(), e);
        }
    }
}
