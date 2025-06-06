package fr.ensitech;

import fr.ensitech.entity.User;
import fr.ensitech.model.IUserDao;
import fr.ensitech.model.UserDao;
import java.util.Map;
import java.util.Set;

public class MainGetUsersByCity {

  public static void main(String[] args) {

    try {
      System.out.println("Starting the application to get users by cities...");
      IUserDao userDao = new UserDao();
//      IAddressDao addressDao = new AddressDao();
//
//      List<String> cities = addressDao.getAllCities();
//
//      for (String city : cities) {
//        System.out.println("\nCity: " + city);
//
//        Map<String, Set<User>> usersByCity = userDao.getUsersByCity(String.valueOf(city));
//        List<User> users = usersByCity.get(city);
//        Set<User> uniqueUsers  = new HashSet<>(users);
//
//        if (uniqueUsers != null && !uniqueUsers.isEmpty()) {
//          for (User user : uniqueUsers) {
//            System.out.println("  - " + user.getName() + " " + user.getLastName() + ", " + user.getEmail());
//          }
//        } else {
//          System.out.println("No users found for this city.");
//        }
//      }

      Map<String, Set<User>> map = userDao.getUsersByCity();
      for (Map.Entry<String, Set<User>> entry : map.entrySet()) {

        System.out.println("\nCity: " + entry.getKey());
        for (User user : entry.getValue()) {
          System.out.println(
              "  - " + user.getName() + " " + user.getLastName() + ", " + user.getEmail());
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Error retrieving users by city", e);
    }
  }

}
