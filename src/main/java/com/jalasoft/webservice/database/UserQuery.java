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

import com.jalasoft.webservice.entitities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to handle user requests to the database.
 */
public class UserQuery {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ROLE = "role";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String USER = "user";
    Connection conn = null;

    /**
     * DBQuery Constructor that initialize the Connection DB
     */
    public UserQuery() {
        conn = ConnectionDB.getInstance().getConn();
    }

    /**
     * Insert method to add a user in user table
     *
     * @param user
     * @return
     */
    public boolean insert(User user) {
        LOGGER.info("Insert '{}' user with '{}' role", user.getUserName(), user.getRole());
        String sql = "Insert into user (user, password, role, email) values (?,?,?,?)";

        PreparedStatement statement = null;
        boolean result = false;
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setString(4, user.getEmail());
            result = statement.execute();
        } catch (SQLException e) {
            LOGGER.error("Exception inserting a row: {}", e.getMessage());
        }
        return result;
    }

    /**
     * Get the user given the user name and password
     *
     * @param userName string
     * @param password string
     * @return file path
     */
    public User getUser(String userName, String password) {
        LOGGER.info("Get {} user", userName);
        User user1 = new User();
        String sql = "select user, password, role, email from user where user = ? and password = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user1.setUserName(resultSet.getString(USER));
                user1.setPassword(resultSet.getString(PASSWORD));
                user1.setEmail(resultSet.getString(EMAIL));
                user1.setRole(resultSet.getString(ROLE));
            }
        } catch (SQLException e) {
            LOGGER.error("Exception getting the user: {}", e.getMessage());
        }
        return user1;
    }

    /**
     * Review if the user exist
     *
     * @param userName
     * @param password
     * @return
     */
    public boolean exists(String userName, String password) {
        LOGGER.info("Reviwing if the {} user exists", userName);
        boolean exist = false;
        String sql = "select user, password, role, email from user where user = ? and password = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            exist = resultSet.next();
        } catch (SQLException e) {
            LOGGER.error("Exception checking if the user exist: {}", e.getMessage());
        }
        return exist;
    }
}
