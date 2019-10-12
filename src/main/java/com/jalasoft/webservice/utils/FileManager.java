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
package com.jalasoft.webservice.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * File Manager class
 */
public class FileManager {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Method to store the file in the fileSystem
     *
     * @param filePath
     * @param file
     * @throws IOException
     */
    static public void saveUploadFile(String filePath, MultipartFile file) throws IOException {
        LOGGER.info("Storing {} file if is not empty.", file.getName());
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            createFolder(filePath);
            Path path = Paths.get(String.format("%s%s", filePath, file.getOriginalFilename()));
            Files.write(path, bytes);
        }
    }

    /**
     * Create the folder if it does not exist.
     *
     * @param folderPath
     */
    static public void createFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    /***
     * Method to verify if a file exist
     * @param fullPathFile
     * @return
     */
    static public boolean isFileExist(String fullPathFile) {
        File tmpFile = new File(fullPathFile);
        return tmpFile.exists();
    }

    /***
     * Method to remove file
     * @param fullPathFile
     */
    static public void removeFile(String fullPathFile) {
        if (isFileExist(fullPathFile)) {
            File tmpFile;
            tmpFile = new File(fullPathFile);
            tmpFile.delete();
        }
    }

    /***
     * Method to save text in a file
     * @param text
     */
    static public void saveTextIntoFile(String fullPathFile, String text) {
        createFolder(new File(fullPathFile).getParent());
        BufferedWriter bufferWriter = null;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fullPathFile);
            bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.write(text);
        } catch (IOException e) {
            LOGGER.info("FileManager IOException {}.", e.getMessage());
        } finally {
            try {
                if (bufferWriter != null)
                    bufferWriter.close();

                if (fileWriter != null)
                    fileWriter.close();

            } catch (IOException ex) {
                LOGGER.info("FileManager Exception {}.", ex.getMessage());
            }
        }
    }

    /**
     * Gets the file name without extension.
     *
     * @param fullFileName String full file name.
     * @return String file name without extension.
     */
    static public String getFileNameNoExtension(String fullFileName) {
        int lastIndex = fullFileName.lastIndexOf(".");
        return fullFileName.substring(0, lastIndex);
    }

    /**
     * Gets the file name extension given a full file name e.g.: file.ext
     *
     * @param fullFileName String full file name.
     * @return String the name without extension.
     */
    static public String getFileNameExtension(String fullFileName) {
        int lastIndex = fullFileName.lastIndexOf(".");
        return fullFileName.substring(lastIndex + 1);
    }

    /***
     * Verify if a file is image or not
     * @param fullPathFile
     * @return
     */
    static public boolean isImageFile(String fullPathFile){
        File file = new File(fullPathFile);
        String mimeType = new MimetypesFileTypeMap().getContentType(file);
        LOGGER.info("MimeType {} refers to File {}.", mimeType, file.getName());
        String type = mimeType.split("/")[0].toLowerCase();
        return type.equals("image");
    }

}
