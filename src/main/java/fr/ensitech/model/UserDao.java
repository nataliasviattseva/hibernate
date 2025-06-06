package fr.ensitech.model;

import fr.ensitech.entity.Address;
import fr.ensitech.entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.RollbackException;
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

      Query<User> query = session.createNamedQuery("User::ByCity", User.class);
      query.setParameter("city", city);
      // Using Set to avoid duplicates
      return Set.copyOf(query.list());

    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }

  @Override
  public Map<String, List<User>> getUsersByCity(String city) throws Exception {
    Session session = null;
    try {
      session = HibernateConnector.getSession();

      Query<User> query = session.createNamedQuery("User::ByCity", User.class);
      query.setParameter("city", city);
      List<User> users = query.list();

      Map<String, List<User>> result = new HashMap<>();
      result.put(city, users);

      return result;


    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }
}
