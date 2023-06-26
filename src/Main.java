import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JsonPlaceholderClient client = new JsonPlaceholderClient();

        try {
            String newUserData = "{\"name\":\"Vlad Kononenko\",\"username\":\"vladkononenko\",\"email\":\"VladKononenko@example.com\"}";
            String createdUser = client.createUser(newUserData);
            System.out.println("Create: " + createdUser);

            int userId = 1;
            String updatedUserData = "{\"name\":\"New Name\",\"username\":\"updatedusername\",\"email\":\"updated@gmail.com\"}";
            String updatedUser = client.updateUser(userId, updatedUserData);
            System.out.println("Update: " + updatedUser);

            userId = 1;
            boolean deleted = client.deleteUser(userId);
            if (deleted) {
                System.out.println("Deleted");
            } else {
                System.out.println("Error");
            }

            String allUsers = client.getAllUsers();
            System.out.println("All users: " + allUsers);

            userId = 2;
            String user = client.getUserById(userId);
            System.out.println("User by ID " + userId + ": " + user);

            String username = "Bret";
            String userByUsername = client.getUserByUsername(username);
            System.out.println("User by Username " + username + ": " + userByUsername);

            userId = 1;
            client.getLastPostCommentsByUserId(userId);

            userId = 1;
            String openToDosByUserId = client.getOpenToDos(userId);
            System.out.println("Open TODOs " + openToDosByUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
