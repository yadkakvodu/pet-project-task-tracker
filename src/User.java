import java.sql.*;
import java.util.*;

public class User {

    private static final String URL = "db.url";
    private static final String PASSWORD = "db.password";
    private static final String USERNAME = "db.username";

    public static void main(String[] args) {
//        System.out.println(executeQuery("ki"));
//        System.out.println(containsUsernameOrEmail("yadkakvodu", "dalipkach.ru"));

//        userRegistration("Igor Zavolochkin", "IgorZ14", "IGOZAV@fashik.ru");
        executeQuery("users");

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

    private static boolean containsUsernameOrEmail(String username, String email) {
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


    private static void userRegistration(String username, String password, String email) {
        String SQL = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
        boolean bool = containsUsernameOrEmail(username, email);
        if (!bool) {
            try (Connection conn = getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(SQL)) {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);

                preparedStatement.executeUpdate();

                System.out.println("вставлен");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void authorization(String email, String password) {
        String SQL1 = "SELECT email FROM users WHERE email = ?";
        String SQL2 = "SELECT password FROM users WHERE password = ?";
        try (Connection conn = getConnection(); PreparedStatement preparedStatement1 = conn.prepareStatement(SQL1);
             PreparedStatement preparedStatement2 = conn.prepareStatement(SQL1)) {

            preparedStatement1.setString(1, email);
            preparedStatement2.setString(1, password);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }





}