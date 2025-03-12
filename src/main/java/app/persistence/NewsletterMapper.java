package app.persistence;

import app.entities.Newsletter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewsletterMapper {

    public static List<Newsletter> getAllNewsletters(ConnectionPool connectionPool) throws DatabaseException
    {
        List<Newsletter> newsletterList = new ArrayList<>();
        String sql = "select * from newsletter";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();

                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    LocalDate published = rs.getDate("published").toLocalDate();
                    String filename = rs.getString("filename");
                    String teasertext = rs.getString("teasertext");
                    String thumbnail_name = rs.getString("thumbnail_name");
                    Newsletter newsletter = new Newsletter(id, title, published, filename, teasertext, thumbnail_name);
                    newsletterList.add(newsletter);
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Could not get users from database");
        }
        return newsletterList;
    }
}
