package app.exceptions;

import java.sql.SQLException;

public class DatabaseException extends Exception {
    public DatabaseException(SQLException ex, String message) {
        super(message);
    }

    public DatabaseException(String s) {
    }

    public DatabaseException(String msg, String message) {
    }
}
