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
import com.jalasoft.webservice.utils.PropertiesReader;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static com.jalasoft.webservice.utils.Constants.APPLICATION_PROPERTIES;

public class OrcConvert implements IConvert{
    private PropertiesReader propertiesFile = new PropertiesReader("src/main/resources/", APPLICATION_PROPERTIES);
    private String targetDirectory = "file.target-dir";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public BaseFile Convert(BaseFile model) {
        String tesseractPath = "file.tesseract-path";
        String defaultLanguageCode = "file.default-languageCode";
        String defaultLanguage = "file.default-language";
        OrcFile orcFile = (OrcFile) model;
        String imgFile = orcFile.getFullFileName();
        Tesseract tesseract = new Tesseract();
        if(!Constants.LANGUAGE.containsKey(orcFile.getLang())){
            orcFile.setLang(Constants.LANGUAGE.get(propertiesFile.getValue(defaultLanguage)));
        }
        else
        {
            orcFile.setLang(Constants.LANGUAGE.get(orcFile.getLang()));
        }
        try {
            tesseract.setDatapath(propertiesFile.getValue(tesseractPath));
            if(orcFile.getLang().equals(propertiesFile.getValue(defaultLanguageCode) ))
                tesseract.setLanguage(orcFile.getLang());
            String text = tesseract.doOCR(new File(String.format("%s%s", orcFile.getPath(), imgFile)));
            TextFile textFile = new TextFile();
            textFile.setPath(propertiesFile.getValue(targetDirectory));
            textFile.setFileName(orcFile.getFileName());
            textFile.setFileType("csv");
            textFile.saveTextIntoFile(text);
            return textFile;
        }
        catch (TesseractException e) {
            System.err.println(e.getMessage());
            LOGGER.info("OrcConvert Exception.", e.getMessage());
        }

        return null;

    }
}