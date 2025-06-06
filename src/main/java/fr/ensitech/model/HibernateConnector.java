

package fr.ensitech.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class HibernateConnector {

  private static final SessionFactory sessionFactory;

  static {
    try {
      Configuration config = new Configuration().configure(); // loads hibernate.cfg.xml
      sessionFactory = config.buildSessionFactory();
    } catch (Throwable ex) {
      throw new ExceptionInInitializerError("SessionFactory creation failed: " + ex);
    }
  }

  public static Session getSession() {
    return sessionFactory.openSession();
  }
}
