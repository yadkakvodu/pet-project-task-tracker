import java.sql.*;

public class User {

    private static final String URL = "db.url";
    private static final String PASSWORD = "db.password";
    private static final String USERNAME = "db.username";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                PropertiesUtil.get(URL),
                PropertiesUtil.get(USERNAME),
                PropertiesUtil.get(PASSWORD)
        )) {

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



}