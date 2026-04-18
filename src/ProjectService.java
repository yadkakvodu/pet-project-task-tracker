import javax.xml.transform.Result;
import java.sql.*;

public class ProjectService {

    private static final String URL = "db.url";
    private static final String PASSWORD = "db.password";
    private static final String USERNAME = "db.username";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PropertiesUtil.get(URL),
                PropertiesUtil.get(USERNAME),
                PropertiesUtil.get(PASSWORD));
    }

    protected static void createProject(User user, String name, String description) {

        String SQL = "INSERT INTO projects(name, description, owner_id) VALUES (?, ?, ?);";
        String SQL2 = "INSERT INTO project_members(project_id, role, user_id) VALUES(?, ?, ?);";

        if (UserService.authorization(user) > 0) {

            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement preparedStatement2 = connection.prepareStatement(SQL2)) {

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, description);
                preparedStatement.setInt(3, UserService.authorization(user));

                preparedStatement.executeUpdate();

                var id = preparedStatement.getGeneratedKeys();
                int id_2 = 0;
                while (id.next()) {
                    id_2 = id.getInt(1);
                }

                preparedStatement2.setInt(1, id_2);
                preparedStatement2.setString(2, "admin");
                preparedStatement2.setInt(3, UserService.authorization(user));

                if (id_2 > 0) {
                    preparedStatement2.executeUpdate();
                } else {
                    System.out.println("ошибка");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }

//    ## ✅ Добавление пользователя в проект
//
//     один пользователь добавляет другого
//
//    Код должен:
//
//            1. проверить: добавляющий — admin
//2. проверить: пользователь существует
//3. проверить: его ещё нет в проекте
//4. добавить в project_members


    protected static void addUserAtProject(User admin, User member, Integer project_id) {

        String SQL = "SELECT role FROM project_members WHERE project_id = ? AND user_id = ?";
        String SQL2 = "SELECT user_id FROM project_members WHERE project_id = ?";
        String SQL3 = "INSERT INTO project_members(project_id, role, user_id) VALUES(?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL);
             PreparedStatement preparedStatement2 = connection.prepareStatement(SQL2);
             PreparedStatement preparedStatement3 = connection.prepareStatement(SQL3)) {

            preparedStatement.setInt(1, project_id);
            preparedStatement.setInt(2, UserService.authorization(admin));

            preparedStatement2.setInt(1, project_id);
            ResultSet resultSet2 = preparedStatement2.executeQuery();

            int id = 0;
            while (resultSet2.next()) {
                if (resultSet2.getInt("user_id") == UserService.authorization(member)) {
                    id++;
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("role").equals("admin")) {
                    if (UserService.authorization(member) > 0 && id == 0) {
                        preparedStatement3.setInt(1, project_id);
                        preparedStatement3.setString(2, "member");
                        preparedStatement3.setInt(3, UserService.authorization(member));

                        preparedStatement3.executeUpdate();
                        System.out.println("Пользователь добавлен");
                    } else {
                        System.out.println("Пользователь есть в проекту!");
                    }
                } else {
                    System.out.println("Только админ может добавлять участника в проект!");
                }
            } else {
                System.out.println("Неизвестная ошибка");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



}
