package fr.ensitech;

import fr.ensitech.entity.User;
import fr.ensitech.model.IUserDao;
import fr.ensitech.model.UserDao;
import fr.ensitech.utils.Dates;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.crypto.Data;

public class MainGetNameLastNameEmailByBirthDate {

  public static void main(String[] args) throws ParseException {

    Date dateInf = Dates.convertStringToDate("01/01/2001"); // 01/01/1980
    Date dateSup = Dates.convertStringToDate("01/01/2021"); // 01/01/2000
//    Date dateInf = new Date(101, 0, 1); // 01/01/1980
//    Date dateSup = new Date(120, 0, 1); // 01/01/2000

    try {
      System.out.println("Starting the application to get users by birth date...");
      IUserDao userDao = new UserDao();

      Map<String, List<String>> map = userDao.getNameLastNameEmailByBirthDate(dateInf, dateSup);

      for (Map.Entry<String, List<String>> entry : map.entrySet()) {
        System.out.println("Email: " + entry.getKey());
        System.out.println("  -> Name:" + entry.getValue().get(0));
        System.out.println("  -> Last Name:" + entry.getValue().get(1));
        System.out.println("  -> Birth Date:" + entry.getValue().get(2));
      }


    } catch (Exception e) {
      throw new RuntimeException("Error retrieving users by city", e);
    }
  }

}
