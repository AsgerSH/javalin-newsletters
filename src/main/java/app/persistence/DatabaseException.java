package app.persistence;

import java.sql.SQLException;

public class DatabaseException extends Exception {
    public DatabaseException(SQLException ex, String message) {
        super(message);
    }
}
