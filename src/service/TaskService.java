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

    public static void createTask(User taskCreator, Project project, Task task) {
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
                preparedStatement1.setString(4, task.getTitle());
                preparedStatement1.setString(5, task.getPriority());
                preparedStatement1.setString(6, task.getDescription());

                preparedStatement1.executeUpdate();
                System.out.println("Задача добавлена");

            } else {
                System.out.println("Ошибка");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int getTaskId(Task task) {

        String sql = "SELECT id FROM tasks WHERE title = ? AND description = ? AND priority = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, task.getPriority());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } return -1;
    }

    public static void updateTaskAssignee(Project project, Task task, User assignee) {
        if (getTaskId(task) > 0) {
            String SQL = "SELECT project_id FROM project_members WHERE user_id = ?";
            String SQL2 = "UPDATE tasks SET assignee_id = ? WHERE id = ?;";
            String SQL3 = "INSERT INTO task_history(task_id, field_name)" +
                    "VALUES(?, ?)";
            int id = UserService.authorization(assignee);

            try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL);
                 PreparedStatement preparedStatement2 = connection.prepareStatement(SQL2);
                 PreparedStatement preparedStatement3 = connection.prepareStatement(SQL3)) {

                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    if (resultSet.getInt("project_id") == ProjectService.getProjectId(project)) {
                        preparedStatement2.setInt(1, id);
                        preparedStatement2.setInt(2, getTaskId(task));

                        preparedStatement2.executeUpdate();
                    }
                }

                preparedStatement3.setInt(1, getTaskId(task));
                preparedStatement3.setString(2, "assignee");
                preparedStatement3.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }



}
