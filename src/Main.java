public class Main {

    public static void main(String[] args) {
//        UserService.userRegistration("RASKOL", "TJV1312", "pravoslavna@mail.ru");

//        User user1 = new User("Yaroslav", "Samusevich", "YarikPercosetik2007");
//        User user2 = new User("Dinislam Alipkachev", "dinislamchik@mail.ru", "Dinislam200720072007");
//        UserService.userRegistration(user2);

        User user3 = new User("Konstantin Gulin", "KGulin@inbox.ru", "Prajskaya1312");
//        UserService.userRegistration(user3);
//        ProjectService.createProject(user3, "MongoDBDriver", "Driver for Mongo DataBase");

        User user4 = new User("Semyon Voronin", "SVO2022@mail.ru", "ZaRussiaZaPutina2022");

//        UserService.userRegistration(user4);
//
//        ProjectService.addUserAtProject(user3, user4, 1);


        ProjectService.getUserProjects(user4);

    }

}