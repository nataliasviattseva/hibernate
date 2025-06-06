package fr.ensitech.model;

import fr.ensitech.entity.Address;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class AddressDao implements IAddressDao {

  @Override
  public Address createAddress(Address address) throws Exception {
    // Implementation for creating an address
    return null;
  }

  @Override
  public Address getAddressById(Long id) throws Exception {
    // Implementation for retrieving an address by ID
    return null;
  }

  @Override
  public Address updateAddress(Address address) throws Exception {
    // Implementation for updating an address
    return null;
  }

  @Override
  public void deleteAddress(Long id) throws Exception {
    // Implementation for deleting an address by ID
  }

  @Override
  public List<Address> getAllAddresses() throws Exception {
    // Implementation for retrieving all addresses
    return null;
  }

  @Override
  public List<String> getAllCities() throws Exception {
    // Implementation for retrieving all cities for a user
    Session session = null;
    try {
      session = HibernateConnector.getSession();
      Query<String> query = session.createQuery("SELECT DISTINCT a.city FROM Address a", String.class);

      return query.list();
    } finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }
}


