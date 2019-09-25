
/*
 * Copyright (c) 2019 Jalasoft.
 *
 *  This software is the confidential and proprietary information of Jalasoft.
 *  ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jalasoft.
 */

package com.jalasoft.webservice.entitities;

/***
 * BaseFile : Class generic to manipulate information related to files
 *  Version : 1.0
 *  Date: 9/19/2019
 */
public class BaseFile {
    private String fileName;
    private String fileType;
    private String path;
    private String fullFileName;
    private long size;
    private String checkSum;

    /***
     * Get Full File Name
     * @return
     */
    public String getFullFileName() {
        return String.format("%s.%s", fileName, fileType);
    }

    /***
     * Get File Name
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /***
     * Set File Name
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Get File Type
     * @return
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Set File Type
     * @param fileType
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /***
     * Get Path
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * Set Path
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Get Size
     * @return
     */
    public long getSize() {
        return size;
    }

    /**
     * Set Size
     * @param size
     */
    public void setSize(long size) {
        this.size = size;
    }
}