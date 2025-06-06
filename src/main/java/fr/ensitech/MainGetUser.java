package fr.ensitech;

import fr.ensitech.entity.User;
import fr.ensitech.model.IUserDao;
import fr.ensitech.model.UserDao;

public class MainGetUser {

  public static void main(String[] args) {
    Long user_id = 5L; // Assuming we want to get the user with ID 3

    try {
      System.out.println("Starting the application to get user...");

      IUserDao userDao = new UserDao();
      User user = userDao.getUserById(user_id);

      if (user != null) {
        System.out.println("User found: " + user.getName());
      } else {
        System.out.println("User not found for ID: " + user_id);
      }

    } catch (Exception e) {
      throw new RuntimeException("Error retrieving user", e);
    }
  }

}
