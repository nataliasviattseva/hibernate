package fr.ensitech;

import fr.ensitech.entity.User;
import fr.ensitech.model.IUserDao;
import fr.ensitech.model.UserDao;
import java.util.List;

public class MainGetAllUsers {

  public static void main(String[] args) {

    try {
      System.out.println("Starting the application to get all users...");
      IUserDao userDao = new UserDao();
      List<User> users = userDao.getAllUsers();
      users.forEach(System.out::println);
//      users.forEach(u -> System.out.println("User: " + u.getName() + " " + u.getLastName()));

    } catch (Exception e) {
      throw new RuntimeException("Error retrieving all users", e);
    }

    // Note: The actual implementation to retrieve all users is not provided in this snippet.
    // You would typically call a method from IUserDao or UserDao to fetch all users and print them.

  }

}
