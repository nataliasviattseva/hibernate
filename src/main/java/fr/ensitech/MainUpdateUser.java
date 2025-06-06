package fr.ensitech;

import fr.ensitech.entity.User;
import fr.ensitech.model.IUserDao;
import fr.ensitech.model.UserDao;

public class MainUpdateUser {

  public static void main(String[] args) {

    Long user_id = 1L;

    try {
      System.out.println("Starting the application to update user...");

      IUserDao userDao = new UserDao();
      User user = userDao.getUserById(user_id);

      if (user != null) {
        System.out.println("User found: " + user.getName());
      } else {
        System.out.println("User not found for ID: " + 3);
      }

      assert user != null;
      user.setLastName(user.getLastName().concat("-MAJ"));
      user.setName(user.getName().concat("-MAJ"));
      userDao.updateUser(user);

      User _user = userDao.getUserById(user_id);

      System.out.println("User updated successfully: " + _user);
//      System.out.println("User updated successfully: " + _user.getName() + " " + _user.getLastName());


    } catch (Exception e) {
      throw new RuntimeException("Error retrieving user", e);
    }
  }

}
