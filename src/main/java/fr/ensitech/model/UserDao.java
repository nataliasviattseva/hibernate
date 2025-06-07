package fr.ensitech.model;

import fr.ensitech.entity.Address;
import fr.ensitech.entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDao implements IUserDao {

  @Override
  public User createUser(User user) throws Exception {
    Session session = null;
    Transaction tx = null;

    try {
      session = HibernateConnector.getSession();
      tx = session.beginTransaction();
      session.save(user);
      if (user.getAddresses() != null && !user.getAddresses().isEmpty()) {

        for (Address address : user.getAddresses()) {
//          address.setUser(user); // Set the user for the address
          session.save(address); // Save each address
        }

      }
      tx.commit();
      return user;

    } catch (RollbackException e) {
      tx.rollback();

    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
    return null;
  }

  @Override
  public User getUserById(Long id) throws Exception {
    if (id == null || id <= 0) {
      throw new IllegalArgumentException("ID must be a positive non-zero value.");
    }
    Session session = null;
    try {
      session = HibernateConnector.getSession();
      User user = session.get(User.class, id); // or session.find(User.class, id);
      return user;

    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }

  @Override
  public void updateUser(User user) throws Exception {
    Session session = null;
    Transaction tx = null;

    try {
      session = HibernateConnector.getSession();
      tx = session.beginTransaction();
      session.update(user);
      tx.commit();

    } catch (RollbackException e) {
      tx.rollback();

    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }

  @Override
  public void deleteUser(User user) throws Exception {
    Session session = null;
    Transaction tx = null;

    try {
      session = HibernateConnector.getSession();
      tx = session.beginTransaction();
      session.delete(user); // or session.remove(user);
      tx.commit();

    } catch (RollbackException e) {
      tx.rollback();

    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }

  @Override
  public List<User> getAllUsers() throws Exception {
    Session session = null;
    try {
      session = HibernateConnector.getSession();
      // Requete native (SQL)
//      Query<User> query = session.createNativeQuery("SELECT * FROM user", User.class);
//      return query.list();

      // Requete JPQL
//      Query<User> query = session.createQuery("SELECT u FROM User u", User.class);
//      return query.list();

      // Requete predefined (named Query)
      Query<User> query = session.createNamedQuery("User::findAll", User.class);
      return query.list();

    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }

  @Override
  public List<User> getUsersByNameAndLastName(String name, String lastName) throws Exception {
    Session session = null;
    try {
      session = HibernateConnector.getSession();
      Query<User> query = session.createNamedQuery("User::ByNameAndLastName", User.class);
      query.setParameter(1, name);
      query.setParameter(2, lastName);
      return query.list();
    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }

  @Override
  public Set<User> getUsersOfCity(String city) throws Exception {
    Session session = null;
    try {
      session = HibernateConnector.getSession();

//      Query<User> query = session.createNamedQuery("User::ByCity", User.class);
//      Query<User> query = session.createQuery("SELECT u FROM User u JOIN u.addresses a ON a.city = :city", User.class);
//      query.setParameter("city", city);
      // Using Set to avoid duplicates
//      return query.getResultList();
//      return Set.copyOf(query.list());
//      return query.stream().collect(Collectors.toSet());

      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<User> query = cb.createQuery(User.class);
      Root<User> userRoot = query.from(User.class);
      query.select(userRoot)
          .where(cb.equal(userRoot.join("addresses").get("city").as(String.class), city));
      return session.createQuery(query).stream()
          .collect(Collectors.toSet());

    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }

  @Override
  public Map<String, Set<User>> getUsersByCity() throws Exception {
    Session session = null;
    try {
      session = HibernateConnector.getSession();

//      Query<User> query = session.createNamedQuery("User::ByCity", User.class);
//      query.setParameter("city", city);
//      List<User> users = query.list();
//
//      Map<String, List<User>> result = new HashMap<>();
//      result.put(city, users);
//
//      return result;

      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<String> query = cb.createQuery(String.class);

      Root<Address> address = query.from(Address.class);
//      query.select(address.get("city").as(String.class))..groupBy(address.get("city").as(String.class));
      query.select(address.get("city").as(String.class)).distinct(true);
      List<String> cities = session.createQuery(query).list();
      Map<String, Set<User>> map = new HashMap<>();

      for (String c : cities) {
        Set<User> usersOfCity = getUsersOfCity(c);
        map.put(c, usersOfCity);
      }
      return map;

    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }

  @Override
  public Map<String, List<String>> getNameLastNameEmailByBirthDate(Date dateInf, Date dateSup)
      throws Exception {

//    Session session = null;
//    try {
//      session = HibernateConnector.getSession();
//      Criteria criteria = session.createCriteria(User.class);
//      List<User> users = criteria.add(Restrictions.between("birthDate", dateInf, dateSup)).list();
//      Map<String, List<String>> result = new HashMap<>();
//
//      for (User user : users) {
//        List<String> NameLastName = new ArrayList<>();
//        NameLastName.add(0, user.getName());
//        NameLastName.add(1, user.getLastName());
//        NameLastName.add(2, user.getBirthDate().toString());
//        result.put(user.getEmail(), NameLastName);
//      }
//      return result;
//
//    } finally {
//      if (session != null && session.isOpen()) {
//        session.close();
//      }
//    }

    Session session = null;
    try {
      session = HibernateConnector.getSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<User> query = cb.createQuery(User.class);
      Root<User> userRoot = query.from(User.class);
      query.select(userRoot)
          .where(cb.between(userRoot.get("birthDate").as(Date.class), dateInf, dateSup)).orderBy(
              cb.asc(userRoot.get("name").as(Date.class)));
      List<User> users = session.createQuery(query).list();
      Map<String, List<String>> result = new HashMap<>();

      for (User user : users) {
        List<String> NameLastName = new ArrayList<>();
        NameLastName.add(0, user.getName());
        NameLastName.add(1, user.getLastName());
        NameLastName.add(2, user.getBirthDate().toString());
        result.put(user.getEmail(), NameLastName);
      }

      return result;

    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }
}

