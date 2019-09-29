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

    /**
     * Get path to specific checksum
     *
     * @param checksum
     * @return
     */
    public static String getPath(String checksum) {
        return new DBQuery().getPath(checksum);
    }

    /***
     * Insert checksum information related to File
     * @param checksum
     * @param path
     */
    public static boolean addFile(String checksum, String path) {
        return new DBQuery().insert(checksum, path);
    }
}
