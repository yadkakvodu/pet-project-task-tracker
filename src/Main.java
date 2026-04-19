import model.Project;
import model.User;
import service.TaskService;

public class Main {

    public static void main(String[] args) {
//        service.UserService.userRegistration("RASKOL", "TJV1312", "pravoslavna@mail.ru");

//        model.User user1 = new model.User("Yaroslav", "Samusevich", "YarikPercosetik2007");
//        model.User user2 = new model.User("Dinislam Alipkachev", "dinislamchik@mail.ru", "Dinislam200720072007");
//        service.UserService.userRegistration(user2);

        User user3 = new User("Konstantin Gulin", "KGulin@inbox.ru", "Prajskaya1312");
//        service.UserService.userRegistration(user3);
//        service.ProjectService.createProject(user3, "MongoDBDriver", "Driver for Mongo DataBase");
        Project project1 = new Project("MongoDBDriver", "Driver for Mongo DataBase");

        User user4 = new User("Semyon Voronin", "SVO2022@mail.ru", "ZaRussiaZaPutina2022");

//        service.UserService.userRegistration(user4);
//
//        service.ProjectService.addUserAtProject(user3, user4, 1);


//        service.ProjectService.getUserProjects(user4);

        TaskService.createTask(user4, project1, "medium", "Create buyTicket method", "void-method of buy ticket");

    }

}