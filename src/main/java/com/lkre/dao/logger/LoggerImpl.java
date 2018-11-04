package com.lkre.dao.logger;

import com.lkre.dao.DatabaseConnection.ConnectionFactory;
import com.lkre.dao.DatabaseConnection.ConnectionFactoryImpl;
import com.lkre.models.Log;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoggerImpl implements Logger {
    private final String SELECT_ALL = "SELECT * FROM tb_stats";
    private ConnectionFactory connectionFactory = new ConnectionFactoryImpl();

    public void log(Site site, Activity activity, String details) {
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

    @Override
    public List<Log> getLogs() {
        List<Log> logs = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connectionFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SELECT_ALL);
            while (rs.next()) {
                int id = rs.getInt("stt_id");
                Date created = rs.getDate("stt_created");
                String site = rs.getString("stt_site");
                String ip = rs.getString("stt_ip");
                String activity = rs.getString("stt_activity");
                String details = rs.getString("stt_details");
                String user_agent = rs.getString("stt_user_agent");
                Log log = new Log(id, created, Site.valueOf(site), ip,
                        Activity.valueOf(activity), details, user_agent);
                logs.add(log);
            }
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
        return logs;
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
