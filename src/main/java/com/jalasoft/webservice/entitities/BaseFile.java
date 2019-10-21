
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

import com.jalasoft.webservice.error_handler.ParamsInvalidException;

/***
 * BaseFile : Class generic to manipulate information related to files
 *  Version : 1.0
 *  Date: 9/19/2019
 */
public class BaseFile {
    protected String fileName;
    protected String path;
    protected long size;
    protected String checkSum;
    protected String fullFilePath;

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

    /***
     * Get Path
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * Set Path
     *
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Get Size
     *
     * @return
     */
    public long getSize() {
        return size;
    }

    /**
     * Set Size
     *
     * @param size
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Get Checksum
     *
     * @return
     */
    public String getCheckSum() {
        return checkSum;
    }

    /**
     * setChecksum
     *
     * @param checkSum
     */
    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    /**
     * Get the full file path
     *
     * @return
     */
    public String getFullFilePath() {
        return fullFilePath;
    }

    /**
     * Set the full file path
     *
     * @param fullFilePath
     */
    public void setFullFilePath(String fullFilePath) {
        this.fullFilePath = fullFilePath;
    }

    /**
     * Validate fields method.
     */
    public void validate() throws ParamsInvalidException {
        if (this.fileName == null) {
            throw new ParamsInvalidException(10, "fileName");
        }
        if (this.fileName.isEmpty()) {
            throw new ParamsInvalidException(11, "fileName");
        }
        if (this.path == null) {
            throw new ParamsInvalidException(10, "path");
        }
        if (this.path.isEmpty()) {
            throw new ParamsInvalidException(11, "path");
        }
    }
}