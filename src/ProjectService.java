import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProjectService {

    private static final String URL = "db.url";
    private static final String PASSWORD = "db.password";
    private static final String USERNAME = "db.username";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PropertiesUtil.get(URL),
                PropertiesUtil.get(PASSWORD),
                PropertiesUtil.get(USERNAME));
    }





}
