package fr.ensitech;

import fr.ensitech.entity.Address;
import fr.ensitech.entity.User;
import fr.ensitech.model.IUserDao;
import fr.ensitech.model.UserDao;

public class MainDeleteUser {

  public static void main(String[] args) {

    Long user_id = 15L;

    try {
      System.out.println("Starting the application to delete user...");

      IUserDao userDao = new UserDao();
      User user = userDao.getUserById(user_id);
      userDao.deleteUser(user);

      User _user = userDao.getUserById(user_id);

      if (_user != null) {
        System.out.println("User found: " + user.getName());
      } else {
        System.out.println("User not found for ID: " + user_id);
      }

    } catch (Exception e) {
      throw new RuntimeException("Error deleting user", e);
    }
  }

}
