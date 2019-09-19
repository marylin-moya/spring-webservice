
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


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/***
 * ModelFile : Class generic to manipulate information related to files
 */
public class ModelFile {
    private String fileName;
    private String fileTtype;
    private String path;
    private long size;
    private String checkSum;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileTtype() {
        return fileTtype;
    }

    public void setFileTtype(String fileTtype) {
        this.fileTtype = fileTtype;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean IsFileExist()
    {
        File tmpFile;
        tmpFile = new File(this.path + this.fileName);
        return tmpFile.exists();
    }

    /***
     * Get information about CheckSum 
     * @return
     */
    public String getCheckSum()
    {
        if(IsFileExist())
        {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256"); //SHA, MD2, MD5, SHA-256, SHA-384...
                DigestInputStream dis = new DigestInputStream(new FileInputStream(this.path + this.fileName), md);
                while (dis.read() != -1) ; //empty loop to clear the data
                md = dis.getMessageDigest();

                // bytes to hex
                StringBuilder result = new StringBuilder();
                for (byte b : md.digest()) {
                    result.append(String.format("%02x", b));
                }
                return result.toString();

            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } ;
        }
        return  null;
    }
}
