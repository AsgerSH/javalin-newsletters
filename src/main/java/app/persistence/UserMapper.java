package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static List<User> getAllUsers(ConnectionPool connectionPool) throws DatabaseException {
        List<User> userList = new ArrayList<>();
        String sql = "select * from users";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();

                while (rs.next())
                {
                    String email = rs.getString("username");
                    String password = rs.getString("password");
                    User user = new User(email, password);
                    userList.add(user);
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Could not get users from database");
        }
        return userList;
    }
}
