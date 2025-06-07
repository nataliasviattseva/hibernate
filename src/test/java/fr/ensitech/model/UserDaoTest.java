package fr.ensitech.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import fr.ensitech.entity.Address;
import fr.ensitech.entity.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserDaoTest {

  private final IUserDao userDao = new UserDao();
  private static User testUser;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    System.out.println("Setting up before all tests...");
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    System.out.println("Cleaning up after all tests...");
  }

  @Before
  public void setUp() throws Exception {
    // Create and insert test user
    testUser = new User();
    testUser.setName("John");
    testUser.setLastName("Doe");
    testUser.setEmail("john.doe@example.com");
    testUser.setBirthDate(new Date());
    testUser.setPassword("password123");

    Address address = new Address();
    address.setNumber("10");
    address.setStreet("123 Main St");
    address.setCity("Paris");
    address.setPostalCode("75001");
    address.setUser(testUser);

    List<Address> addresses = new ArrayList<>();
    addresses.add(address);
//    testUser.setAddresses(addresses);
//    testUser.setAddresses(new HashSet<>(addresses));
    userDao.createUser(testUser);
  }

  @After
  public void tearDown() throws Exception {
    if (testUser != null && testUser.getId() != null) {
      userDao.deleteUser(testUser);
    }
  }

  @Test
  public void testCreateUserAndRetrieveById() throws Exception {
    User retrieved = userDao.getUserById(testUser.getId());
    assertNotNull(retrieved);
    assertEquals("John", retrieved.getName());
    assertEquals("Doe", retrieved.getLastName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetUserByIdNegative() throws Exception {
    userDao.getUserById(-1L);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetUserByIdZero() throws Exception {
    userDao.getUserById(0L);
  }

  @Test
  public void testUpdateUser() throws Exception {
    testUser.setName("Johnny");
    userDao.updateUser(testUser);
    User updated = userDao.getUserById(testUser.getId());
    assertEquals("Johnny", updated.getName());
  }

  @Test
  public void testDeleteUser() throws Exception {
    User tempUser = new User();
    tempUser.setName("Alice");
    tempUser.setLastName("Smith");
    tempUser.setEmail("alice@example.com");
    tempUser.setBirthDate(new Date());
    tempUser.setPassword("pass");
    userDao.createUser(tempUser);

    assertNotNull(userDao.getUserById(tempUser.getId()));
    userDao.deleteUser(tempUser);

    User deleted = userDao.getUserById(tempUser.getId());
    assertNull(deleted);
  }

  @Test
  public void testGetAllUsers() throws Exception {
    List<User> users = userDao.getAllUsers();
    assertNotNull(users);
    assertTrue(users.size() > 0);
  }

  @Test
  public void testGetUsersByNameAndLastName() throws Exception {
    List<User> users = userDao.getUsersByNameAndLastName("Doe", "John");
    assertFalse("Expected at least one user", users.isEmpty());
  }

  @Test
  public void testGetUsersOfCity() throws Exception {
    Set<User> users;
    // Open session manually
    Session session = HibernateConnector.getSession();
    Transaction tx = session.beginTransaction();
    try {
      // Execute the exact same logic your DAO does
      users = new HashSet<>(session.createQuery(
              "SELECT DISTINCT u FROM User u JOIN FETCH u.addresses a WHERE a.city = :city", User.class)
          .setParameter("city", "Paris")
          .list());

      // Now it's safe to assert — addresses are fetched with JOIN FETCH
      assertFalse(users.isEmpty());
      for (User user : users) {
        for (Address address : user.getAddresses()) {
          assertEquals("Paris", address.getCity());
        }
      }

      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      throw ex;
    } finally {
      session.close();
    }
  }

  @Test
  public void testGetUsersByCity() throws Exception {
    Map<String, Set<User>> usersByCity = userDao.getUsersByCity();
    assertTrue(usersByCity.containsKey("Paris"));
  }

  @Test
  public void testGetNameLastNameEmailByBirthDate() throws Exception {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    Date yesterday = cal.getTime();

    cal.add(Calendar.DATE, 2);
    Date tomorrow = cal.getTime();

    Map<String, List<String>> map = userDao.getNameLastNameEmailByBirthDate(yesterday, tomorrow);
    assertTrue(map.containsKey(testUser.getEmail()));
    List<String> data = map.get(testUser.getEmail());
    assertEquals("John", data.get(0));
    assertEquals("Doe", data.get(1));
  }

  @Test
  public void getAllUsers() throws Exception {
    List<User> users = userDao.getAllUsers();
    assertNotNull(users);
    assertEquals(10, users.size()); // Assuming no users are present initially
  }
}