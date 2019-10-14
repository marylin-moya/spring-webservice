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
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class to handle database connections.
 */
public class ConnectionDB {
    private static final Logger LOGGER = LogManager.getLogger();
    private static ConnectionDB instance;
    private static Connection conn;

    /**
     * Gets the database connection.
     *
     * @return Connection with the database.
     */
    public static Connection getConn() {
        return conn;
    }

    /**
     * ConnectionDB Constructor.
     */
    private ConnectionDB() {
        this.init();
    }

    /**
     * Method to create the webservice database if does not exist with a fileST and user tables
     * fileST table has an id, checksum and path.
     */
    private void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:webservice.db");
            Statement state = conn.createStatement();
            state.execute("create table if not exists fileST (id integer primary key, checksum varchar(32), path varchar(250));");
            state.execute("create table if not exists user (id integer primary key, user varchar(32), password varchar(32), role varchar(250), email varchar(250));");
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error("Exception while initializing the database: {}", e.getMessage());
        }
    }

    /**
     * Gets the ConnectionDB instance if this exist or creates a new one if doesn't.
     *
     * @return ConnectionDB instance
     */
    public static ConnectionDB getInstance() {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }
}
