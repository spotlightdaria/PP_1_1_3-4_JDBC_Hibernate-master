package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Flin", "Black", (byte) 25);
        userService.saveUser("Jan", "Red", (byte) 33);
        userService.saveUser("Mark", "Green", (byte) 45);
        userService.saveUser("Jon", "Brown", (byte) 18);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();

    }
}
