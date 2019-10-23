package com.lkre.dao.logger;

import com.lkre.dao.DatabaseConnection.ConnectionFactory;
import com.lkre.dao.DatabaseConnection.ConnectionFactoryImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@ManagedBean
@ViewScoped
public class Logger {
    private final String TB_STATS_NAME = "tb_stats";
    private final String SITE_NAME = "stt_site";
    private final String INSERT_LOG = "INSERT INTO " + TB_STATS_NAME
            + " (stt_created, stt_activity, stt_site, stt_ip, stt_user_agent, stt_details) "
            + "VALUES (?, ?, ?, ?, ?, ?) ";
    private final String COUNT = "SELECT COUNT(*) FROM " + TB_STATS_NAME;
    private final String COUNT_PERIOD =
            "SELECT COUNT(*) FROM " + TB_STATS_NAME + " WHERE stt_created >= ? AND " + SITE_NAME + " = ?";

    private ConnectionFactory connectionFactory = new ConnectionFactoryImpl();

    public void log(
            Site site,
            Activity activity,
            String details
    ) {
        Timestamp currentTimestamp =
                new Timestamp(new java.util.Date().getTime());
        Connection connection = null;
        try {
            connection = connectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_LOG);
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

    public int count() {
        Connection connection = null;
        int count = 0;
        try {
            connection = connectionFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(COUNT);
            rs.next();
            count = rs.getInt(1);
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
        return count;
    }

    public int count(
            Site site,
            Period period
    ) {
        LocalDate localDate = LocalDate.now();
        switch (period) {
            case TODAY:

                break;
            case WEEK:
                localDate = localDate.minusDays(7);
                break;
            case MONTH:
                localDate = localDate.minusMonths(1);
                break;
        }
        Date date =
                Date.from(localDate.atStartOfDay(ZoneId.systemDefault())
                                   .toInstant());
        Timestamp fromTimeStamp = new Timestamp(date.getTime());
        Connection connection = null;
        int count = 0;
        try {
            connection = connectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    COUNT_PERIOD);
            preparedStatement.setTimestamp(1, fromTimeStamp);
            preparedStatement.setString(2, site.name());
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            count = rs.getInt(1);
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
        return count;
    }

    private static String getIpAdress() {
        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance()
                                                 .getExternalContext()
                                                 .getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    private static String getUserAgent() {
        final HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance()
                                                 .getExternalContext()
                                                 .getRequest();
        return request.getHeader("user-agent");
    }
}
