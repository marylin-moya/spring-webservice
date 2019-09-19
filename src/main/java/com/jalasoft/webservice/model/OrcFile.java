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

import com.jalasoft.webservice.utils.ConfigurationVariable;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.*;

/****
 * OrcFile : Class to convert image to text
 */
public class OrcFile extends ModelFile {
    private String lang;

    /***
     * get text from image file, according the language
     * @param lang
     * @return
     */
    public TextFile getTextFile(String lang)
    {
        String imgFile = getFullFileName();
        Tesseract tesseract = new Tesseract();
        if(!ConfigurationVariable.LANGUAGE.containsKey(lang)){
            return null;
        }
        if (!lang.equals(ConfigurationVariable.DEFAULT_LANGUAGE)){
            this.lang = ConfigurationVariable.LANGUAGE.get(lang);
        }
        try {
            tesseract.setDatapath(ConfigurationVariable.TESSERAL_PATH);
            if(this.lang != null)
                tesseract.setLanguage(this.lang);
            String text = tesseract.doOCR(new File(getPath() + imgFile));
            TextFile textFile = new TextFile();
            textFile.setPath(ConfigurationVariable.TXT_FOLDER);
            textFile.setFileName(this.getFileName());
            textFile.setFileType("csv");
            textFile.saveTextIntoFile(text);
            return textFile;
        }
        catch (TesseractException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
