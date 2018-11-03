package com.lkre.dao.logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;

public class LoggerImpl implements Logger {
    public void log(Site site, Activity activity, String details) {
        Timestamp currentTimestamp = new Timestamp(new java.util.Date().getTime());
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO TB_STATS (stt_created, stt_activity, stt_site, stt_ip, stt_user_agent, stt_details) " +
                            "VALUES (?, ?, ?, ?, ?, ?) ");
//            preparedStatement.setInt(1, id);
            preparedStatement.setTimestamp(1, currentTimestamp);
            preparedStatement.setString(2, activity.name());
            preparedStatement.setString(3, site.name());
            preparedStatement.setString(4, getIpAdress());
            preparedStatement.setString(5, getUserAgent());
            preparedStatement.setString(6, details);
            int i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Connection getConnection() throws SQLException {
        String username = "aiqyaaubzvwhay";
        String password = "846d2a17be73677893f0594ceaddb64634b7726c20d7e41ad0637917f5977a0a";
        String dbUrl = "jdbc:postgresql://ec2-46-51-184-229.eu-west-1.compute.amazonaws.com:5432/d5jfnje0l7sc5m?sslmode=require";
        return DriverManager.getConnection(dbUrl, username, password);
    }

    private static String getIpAdress() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;

    }

    private static String getUserAgent() {
        final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getHeader("user-agent");
    }

}
