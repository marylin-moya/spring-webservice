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


    public TextFile getTextFile(String imgFile, String lang)
    {
        Tesseract tesseract = new Tesseract();

        try {

            tesseract.setDatapath(ConfigurationVariable.TESSERAL_PATH);
            String text = tesseract.doOCR(new File(ConfigurationVariable.IMG_FOLDER + imgFile));
            String fileName = imgFile.split("\\.")[0];
            TextFile textFile = new TextFile();
            saveTextIntoFile(text, fileName);
            textFile.setFileName(fileName);
            textFile.setFileTtype("csv");
            textFile.setText(text);

            return textFile;
        }
        catch (TesseractException e) {
            e.printStackTrace();
        }

        return null;
    }

    /***
     * Method to get the text which is in image file
     * @param text
     * @param fileName
     */
    private void saveTextIntoFile(String text, String fileName)
    {
        String csvFile = ConfigurationVariable.TXT_FOLDER + fileName+".csv";
        BufferedWriter bufferWriter = null;
        FileWriter fileWriter = null;
        try {

            fileWriter = new FileWriter(csvFile);
            bufferWriter = new BufferedWriter(fileWriter);

            bufferWriter.write(text);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferWriter != null)
                    bufferWriter.close();

                if (fileWriter != null)
                    fileWriter.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
