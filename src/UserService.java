import java.sql.*;
import java.util.*;

public class UserService {

    private static final String URL = "db.url";
    private static final String PASSWORD = "db.password";
    private static final String USERNAME = "db.username";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PropertiesUtil.get(URL),
                PropertiesUtil.get(USERNAME),
                PropertiesUtil.get(PASSWORD)
        );
    }

    protected static List<Integer> executeQuery(String SQL) {
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

    protected static boolean containsUsernameOrEmail(String username, String email) {
        String SQL1 = "SELECT COUNT(*) FROM users WHERE username = ?";
        String SQL2 = "SELECT COUNT(*) FROM users WHERE email = ?";
        int cnt = 0;

        try (Connection conn = getConnection(); PreparedStatement preparedStatement1 = conn.prepareStatement(SQL1);) {
            PreparedStatement preparedStatement2 = conn.prepareStatement(SQL2);

            preparedStatement1.setString(1, username);
            preparedStatement2.setString(1, email);

            ResultSet res1 = preparedStatement1.executeQuery();
            ResultSet res2 = preparedStatement2.executeQuery();

            if (res1.next()) {
                if (res1.getInt(1) > 0) {
                    cnt++;
                }
            }

            if (res2.next()) {
                if (res2.getInt(1) > 0) {
                    cnt++;
                }
            }
            return cnt > 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cnt > 0;
    }


        protected static void userRegistration(User user) {
        String SQL = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
        boolean bool = containsUsernameOrEmail(user.getUsername(), user.getEmail());
        if (!bool) {
            try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(SQL)) {

                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getPassword());

                preparedStatement.executeUpdate();

                System.out.println("Insert Complete");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    protected static int authorization(User user) {

        String SQL1 = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = getConnection(); PreparedStatement preparedStatement1 = conn.prepareStatement(SQL1);) {

            preparedStatement1.setString(1, user.getEmail());
            preparedStatement1.setString(2, user.getPassword());

            ResultSet rs1 = preparedStatement1.executeQuery();

            if (rs1.next()) {
                return rs1.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }


}