package service;

import model.Project;
import model.Task;
import model.User;

import java.sql.*;

public class TaskService {

    private static final String URL = "db.url";
    private static final String PASSWORD = "db.password";
    private static final String USERNAME = "db.username";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(PropertiesUtil.get(URL),
                PropertiesUtil.get(USERNAME),
                PropertiesUtil.get(PASSWORD));
    }

    public static void createTask(User taskCreator, Project project, String priority, String title, String description) {
        String SQL = "SELECT user_id FROM project_members WHERE project_id = ? AND user_id = ?;";
        String SQL1 = "INSERT INTO tasks(assignee_id, creator_id, project_id, title, priority, description) VALUES (?, ?, ?, ?, ?, ?);";

        int user_id = UserService.authorization(taskCreator);
        int project_id = ProjectService.getProjectId(project);

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL);
             PreparedStatement preparedStatement1 = connection.prepareStatement(SQL1)) {

            preparedStatement.setInt(1, project_id);
            preparedStatement.setInt(2, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            int user_id2 = -1;
            while (resultSet.next()) {
                user_id2 = resultSet.getInt("user_id");
            }

            if (user_id2 > 0) {
                preparedStatement1.setInt(1, user_id);
                preparedStatement1.setInt(2, user_id);
                preparedStatement1.setInt(3, project_id);
                preparedStatement1.setString(4, title);
                preparedStatement1.setString(5, priority);
                preparedStatement1.setString(6, description);

                preparedStatement1.executeUpdate();
                System.out.println("Задача добавлена");
            } else {
                System.out.println("Ошибка");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



//    👉 меняем assignee
//
//    Код должен:
//
//            1. проверить: задача существует
//2. проверить: пользователь в проекте
//3. проверить: новый исполнитель в проекте
//4. обновить задачу
//5. записать в history

    protected static void updateTaskAssignee(Project project, Task task, User assignee) {



    }

}
