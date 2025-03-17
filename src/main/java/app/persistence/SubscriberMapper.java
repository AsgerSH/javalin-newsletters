package app.persistence;

import app.entities.Newsletter;
import app.entities.Subscriber;
import app.exceptions.DatabaseException;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SubscriberMapper {

    public static List<Subscriber> getAllSubscribers(ConnectionPool connectionPool) throws DatabaseException {
        List<Subscriber> subscriberList = new ArrayList<>();
        String sql = "select * from subscriber";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String email = rs.getString("username");
                    LocalDate date = rs.getDate("date").toLocalDate();
                    Subscriber subscriber = new Subscriber(email, date);
                    subscriberList.add(subscriber);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not get users from database");
        }
        return subscriberList;
    }

    public Subscriber insertSubscriber(ConnectionPool connectionPool, String email) throws DatabaseException {
        String sql = "INSERT INTO subscriber (email, created) VALUES (?, CURRENT_DATE) ON CONFLICT (email) DO NOTHING;";
        Subscriber subscriber;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                int result = ps.executeUpdate();
                subscriber = new Subscriber(email, LocalDate.now());

//                if (rs.next()) {
//                    String email = rs.getString("email");
//                    LocalDate created = rs.getDate("created").toLocalDate();
//                    ps.setString(1, rs.getString(1));
//                    Subscriber subscriber = new Subscriber(email, LocalDate.now());
//                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not insert subscriber to database");
        }

        return subscriber;
    }

    public boolean deleteSubscriber(ConnectionPool connectionPool, String email) throws DatabaseException {
        String sql = "DELETE FROM subscriber WHERE email = ?";
        Subscriber subscriber;
        boolean action = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                int result = ps.executeUpdate();
                if (result == 1) {
                    action = true;
                }
            }
        }catch (SQLException ex) {
            throw new DatabaseException(ex, "Could not delete subscriber from database");
        }
        return action;
    }

}