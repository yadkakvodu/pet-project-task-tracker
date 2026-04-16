public class Main {

    public static void main(String[] args) {
//        UserService.userRegistration("RASKOL", "TJV1312", "pravoslavna@mail.ru");

        User user1 = new User("Yaroslav", "Samusevich", "YarikPercosetik2007");
        int user1Id = UserService.authorization(user1);
        System.out.println(user1Id);

//        System.out.println(UserService.executeQuery("users"));

    }

}