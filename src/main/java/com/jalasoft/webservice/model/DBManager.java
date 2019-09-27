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

import com.jalasoft.webservice.database.DBQuery;

/***
 * Manage the queries to database
 */
public class DBManager {
    DBQuery dbQuery;
    public DBManager()
    {
        dbQuery = new DBQuery();
    }

    /**
     * Get path to specific checksum
     * @param checksum
     * @return
     */
    public String getFilePath(String checksum)
    {
        return dbQuery.getPath(checksum);
    }

    /***
     * Insert checksum information related to File
     * @param checksum
     * @param path
     */
    public void insertChecksumInformation(String checksum, String path)
    {
        dbQuery.insert(checksum, path);
    }
}
