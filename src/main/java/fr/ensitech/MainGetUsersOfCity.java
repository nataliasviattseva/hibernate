package fr.ensitech;

import fr.ensitech.entity.User;
import fr.ensitech.model.IUserDao;
import fr.ensitech.model.UserDao;
import java.util.Set;
import javax.persistence.Query;

public class MainGetUsersOfCity {

  public static void main(String[] args) {

    String city = "London";
    try {
      System.out.println("Starting the application to get users of city...");

      IUserDao userDao = new UserDao();
      Set<User> users = userDao.getUsersOfCity(city);
      users.forEach(System.out::println);

//      if (users != null && !users.isEmpty()) {
//        System.out.println("Users found in " + city + ":");
//        for (User user : users) {
//          System.out.println(user.getName() + " " + user.getLastName());
//        }
//      } else {
//        System.out.println("No users found for city: " + city);
//      }

    } catch (Exception e) {
      throw new RuntimeException("Error retrieving users of city", e);
    }
  }

}
