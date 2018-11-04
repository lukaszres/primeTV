package com.lkre.dao.logger.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {
    @Override
    public Connection getConnection() throws SQLException {
        String username = "aiqyaaubzvwhay";
        String password = "846d2a17be73677893f0594ceaddb64634b7726c20d7e41ad0637917f5977a0a";
        String dbUrl = "jdbc:postgresql://ec2-46-51-184-229.eu-west-1.compute.amazonaws.com:5432/d5jfnje0l7sc5m?sslmode=require";
        return DriverManager.getConnection(dbUrl, username, password);
    }
}
