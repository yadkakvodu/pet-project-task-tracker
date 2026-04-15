import java.sql.*;
import java.util.*;

public class User {

    private static final String URL = "db.url";
    private static final String PASSWORD = "db.password";
    private static final String USERNAME = "db.username";

    public static void main(String[] args) {
        System.out.println(executeQuery("ki"));
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PropertiesUtil.get(URL),
                PropertiesUtil.get(USERNAME),
                PropertiesUtil.get(PASSWORD)
        );
    }


    private static List<Integer> executeQuery(String SQL) {
        List<Integer> result = new ArrayList<>();
        List<String> tablesNames = Arrays.asList("Comments", "Project Members", "Projects",
                "Status Transitions", "Task History", "Task Statuses", "Tasks", "users");
        if (tablesNames.contains(SQL)) {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + SQL)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    result.add(resultSet.getInt("id"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return result;
        } else {
            System.out.println("error table name");
        } return result;
    }

    private static void userRegistration(String username, String password, String email) {

    }

}