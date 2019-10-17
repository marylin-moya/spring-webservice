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
import com.jalasoft.webservice.database.UserQuery;
import com.jalasoft.webservice.entitities.User;
import com.jalasoft.webservice.error_handler.DatabaseException;

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

    /**
     * Insert user.
     *
     * @param user
     * @return
     */
    public static boolean insertUser(User user) throws DatabaseException {
        boolean userCreated = new UserQuery().insert(user);
        User createdUser = new UserQuery().getUser(user.getUserName(), user.getPassword());
        if (createdUser.getUserName().isEmpty() || createdUser.getUserName() == null) {
            throw new DatabaseException(String.format("Error to create %s user", user.getUserName()));
        }
        return true;
    }

    /**
     * Get a user
     *
     * @param userName
     * @param password
     * @return
     */
    public static User getUser(String userName, String password) throws DatabaseException {
        User user = new UserQuery().getUser(userName, password);
        try {
            if (user.getUserName().isEmpty() || user.getUserName() == null) {
                throw new DatabaseException(String.format("The %s user is not authorized", userName));
            }
        } catch (NullPointerException e) {
            throw new DatabaseException(String.format("The %s user is not authorized", userName));
        }
        return user;
    }

    /**
     * Method to verify if user is in the database
     *
     * @param userName
     * @param password
     * @return
     * @throws DatabaseException
     */
    public static boolean exist(String userName, String password) throws DatabaseException {
        try {
            return new UserQuery().exists(userName, password);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
