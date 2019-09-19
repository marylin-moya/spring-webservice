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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/** TextFile : Class to manipulate information related to CSV files
 * Version : 1.0
 * Date: 9/19/2019
 */

public class TextFile extends ModelFile {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /***
     * Method to save text in a file
     * @param text
     */
    public void saveTextIntoFile(String text) {
        String csvFile = getPath() + getFullFileName();
        BufferedWriter bufferWriter = null;
        FileWriter fileWriter = null;
        setText(text);
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
