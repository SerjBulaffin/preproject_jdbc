package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user1 = new User("Sergey", "Vizer", (byte) 44);
        User user2 = new User("Oleg", "Sorokin", (byte) 43);
        User user3 = new User("Alexey", "Sotnikov", (byte) 26);
        User user4 = new User("Egor", "Bulavin", (byte) 32);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        for (User u : users) {
            userService.saveUser(u.getName(), u.getLastName(), u.getAge());
        }

        List<User> retUser = userService.getAllUsers();
        for (User user : retUser) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();

        //userService.removeUserById(1);

        //Util.closeSession();
    }
}
