package com.jalasoft.webservice.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {

    private static ConnectionDB instance;

    public static Connection getConn() {
        return conn;
    }

    private static Connection conn;

    private ConnectionDB() throws SQLException, ClassNotFoundException {
        this.init();
    }

    private void init() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:webservice.db");
        Statement state = conn.createStatement();
        state.execute("create table if not exists filST (id integer primary key, checksum varchar(32), path varchar(250));");
    }

    public static ConnectionDB getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }
}
