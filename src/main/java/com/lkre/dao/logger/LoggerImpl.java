package com.lkre.dao.logger;

import com.lkre.dao.DatabaseConnection.ConnectionFactory;
import com.lkre.dao.DatabaseConnection.ConnectionFactoryImpl;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LoggerImpl implements Logger {
    private ConnectionFactory connectionFactory;

    public void log(Site site, Activity activity, String details) {
        connectionFactory = new ConnectionFactoryImpl();
        Timestamp currentTimestamp = new Timestamp(new java.util.Date().getTime());
        Connection connection = null;
        try {
            connection = connectionFactory.getConnection();
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
