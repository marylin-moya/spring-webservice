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

package com.jalasoft.webservice.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to handle request to the database.
 */
public class DBQuery {
    private static final Logger LOGGER = LogManager.getLogger();
    Connection conn = null;

    /**
     * DBQuery Constructor that initialize the Connection DB
     */
    public DBQuery() {
        conn = ConnectionDB.getInstance().getConn();
    }

    /**
     * Insert method to add a file in fileST table
     *
     * @param checksum file
     * @param path     file
     * @return true if the query was successfully executed, false otherwise.
     */
    public boolean insert(String checksum, String path) {
        LOGGER.info("Insert '{}' file to convert with '{}' checksum", path, checksum);
        String sql = "Insert into fileST (checksum, path) values (?,?)";

        PreparedStatement statement = null;
        boolean result = false;
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, checksum);
            statement.setString(2, path);
            result = statement.execute();
        } catch (SQLException e) {
            LOGGER.error("Exception inserting a row: {}", e.getMessage());
        }
        return result;
    }

    /**
     * Get the path if the checksum is found.
     *
     * @param checksum file
     * @return file path
     */
    public String getPath(String checksum) { // throws SQLException
        LOGGER.info("Get file path of {} checksum", checksum);
        String path = null;
        String sql = "select path from fileST where checksum = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, checksum);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                path = resultSet.getString("path");
            }
        } catch (SQLException e) {
            LOGGER.error("Exception getting the file path: {}", e.getMessage());
        }
        return path;
    }

    /**
     * Update checksum in database
     *
     * @param path
     * @param checksum
     */
    public void updateChecksum(String path, String checksum) {
        LOGGER.info("Update checksum of file path: {path}", path);
        String sql = "Update fileST set checksum = ? where path = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, checksum);
            statement.setString(2, path);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            LOGGER.error("Exception updating checksum file path: {}", e.getMessage());
        }
    }
}
