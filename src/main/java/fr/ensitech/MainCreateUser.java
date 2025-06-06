package fr.ensitech;

import fr.ensitech.entity.Address;
import fr.ensitech.model.IUserDao;
import fr.ensitech.model.UserDao;
import fr.ensitech.utils.Dates;
import fr.ensitech.entity.User;

public class MainCreateUser {

  public static void main(String[] args) {

    System.out.println("Starting the application to create user...");

    try {

//      Session session = HibernateConnector.getSession();
//      System.out.println("Session created: " + session);

      User user= new User();
      user.setName("Diana");
      user.setLastName("Miller");
      user.setEmail("dianamiller1@mail.com");
      user.setPassword("password123");
      user.setBirthDate(Dates.convertStringToDate("01/01/2010"));
      user.setActive(false);

      Address address1 = new Address();
      address1.setNumber("123");
      address1.setStreet("Main St");
      address1.setCity("London");
      address1.setPostalCode("12345");
      address1.setUser(user); // Set the user for the address
      user.getAddresses().add(address1);

      Address address2 = new Address();
      address2.setNumber("456");
      address2.setStreet("Elm St");
      address2.setCity("London");
      address2.setPostalCode("12345");
      address2.setUser(user); // Set the user for the address
      user.getAddresses().add(address2);

      IUserDao userDao = new UserDao();
      User _user = userDao.createUser(user);

      System.out.println("User created successfully: " + _user);
      System.out.println("User created: " + user);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("Exiting the application.");
    }
  }
}
