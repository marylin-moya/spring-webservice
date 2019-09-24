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
import com.jalasoft.webservice.entitities.OrcFile;
import com.jalasoft.webservice.entitities.TextFile;
import com.jalasoft.webservice.utils.Constants;
import com.jalasoft.webservice.utils.FileManager;
import com.jalasoft.webservice.utils.PropertiesReader;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static com.jalasoft.webservice.utils.Constants.APPLICATION_PROPERTIES;

/**
 * Orc convert class
 * Version : 1.0
 * Date: 9/19/2019
 */
public class OrcConvert implements IConvert {
    private PropertiesReader propertiesFile = new PropertiesReader("src/main/resources/", APPLICATION_PROPERTIES);
    private String targetDirectory = "file.target-dir";
    private static final Logger LOGGER = LogManager.getLogger();
    private String defaultLanguageCode = "file.default-languageCode";
    private String defaultLanguage = "file.default-language";

    @Override
    public BaseFile Convert(BaseFile baseFile) {
        String tesseractPath = "file.tesseract-path";
        String languageCode = propertiesFile.getValue(defaultLanguageCode);
        String language = null;
        OrcFile orcFile = (OrcFile) baseFile;
        Tesseract tesseract = new Tesseract();

        if (!Constants.LANGUAGE.containsKey(orcFile.getLang())) {
            language = Constants.LANGUAGE.get(languageCode);
        } else {
            language = Constants.LANGUAGE.get(orcFile.getLang());
        }
        try {
            tesseract.setDatapath(propertiesFile.getValue(tesseractPath));
            if (!languageCode.equals(language)) {
                tesseract.setLanguage(orcFile.getLang());
            }
            String text = tesseract.doOCR(new File(String.format("%s%s", orcFile.getPath(), orcFile.getFullFileName()))).trim();
            TextFile textFile = new TextFile();
            textFile.setPath(propertiesFile.getValue(targetDirectory));
            textFile.setFileName(orcFile.getFileName());
            textFile.setFileType("csv");
            FileManager.saveTextIntoFile(String.format("%s%s", textFile.getPath(), textFile.getFullFileName()), text);
            textFile.setText(text);
            return textFile;
        } catch (TesseractException e) {
            LOGGER.info("OrcConvert Exception. {}", e.getMessage());
        }
        return null;
    }
}
