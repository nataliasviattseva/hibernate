package fr.ensitech.model;

import fr.ensitech.entity.Address;
import java.util.List;

public interface IAddressDao {

  Address createAddress(Address address) throws Exception;

  Address getAddressById(Long id) throws Exception;

  Address updateAddress(Address address) throws Exception;

  void deleteAddress(Long id) throws Exception;

  List<Address> getAllAddresses() throws Exception;

  List<String> getAllCities() throws Exception;

}
