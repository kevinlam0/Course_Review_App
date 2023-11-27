package edu.virginia.sde.reviews;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReviewDataDriver extends DatabaseDriver{

    public ReviewDataDriver(String sqliteFileName) {super(sqliteFileName);}
    public void createTable() throws SQLException {
        String query = """
                CREATE TABLE IF NOT EXISTS Reviews
                (
                    ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    FOREIGN KEY (Course_ID) REFERENCES Courses(ID) ON DELETE CASCADE,
                    FOREIGN KEY (Username) REFERENCES Users(Username) ON DELETE CASCADE,
                    Review TEXT,
                    
                );
                """;
        PreparedStatement statement = connection.prepareStatement(query);
        statement.execute();
        statement.close();
    }
}
